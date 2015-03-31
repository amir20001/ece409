package com.security;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

	private static final String PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public static void main(String[] args) {
		String identity = getIdentity();
		String userPublicKey = generatePublicKey(identity);
		String ipAddress = getIp();

		try {
			Socket connection = new Socket(ipAddress, Util.PORT);
			Util.sendOverConnection(connection, identity + "," + userPublicKey);
			String data = Util.readFromConnection(connection);
			String[] tokens = data.split(",");
			identity = tokens[0];
			userPublicKey = tokens[1];
			String expiryDate = tokens[2];
			String s = tokens[3];
			String r = tokens[4];
			String message = new StringBuilder().append(identity).append(userPublicKey).append(expiryDate).toString();
			if (verifiySignature(s, r, message)) {
				System.out.println("Accept the CA's signature and the mini-certificate is valid!");
			} else {
				System.out.println("Mini-certificate is not valid");
			}
			System.out.println("The mini-certificate issued by the CA is:");
			System.out.println(message);
			System.out.println(r);
			System.out.println(s);

		} catch (UnknownHostException e) {
			System.out.println("could not connect to server, exiting");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String getIdentity() {
		String identity = "";
		while (identity.length() > 10 || identity.length() == 0) {
			System.out.println("enter the user's identity");
			identity = Util.readLine();
		}
		return identity;
	}

	private static String getIp() {
		String ip = "";
		while (!validateIp(ip)) {
			System.out.println("Enter the IP address of CA:");
			ip = Util.readLine();
		}
		return ip;
	}

	private static boolean validateIp(final String ip) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	private static String generatePublicKey(String identity) {
		BigInteger publicKey = BigInteger.probablePrime(128 * 8, new Random(identity.hashCode()));
		return publicKey.toString();
	}

	private static boolean verifiySignature(String sString, String rString, String message) {
		PublicKeys keys = PublicKeys.getInstance();
		BigInteger s = new BigInteger(sString);
		BigInteger r = new BigInteger(rString);
		if (Util.isLessThan(r, BigInteger.ZERO) || Util.isGreaterThan(r, keys.q))
			return false;
		BigInteger w = s.modInverse(keys.q);
		BigInteger u1 = w.multiply(Util.sha1Message(message)).mod(keys.q);
		BigInteger u2 = w.multiply(r).mod(keys.q);
		BigInteger v = keys.publicKey.modPow(u2, keys.p).multiply(keys.g.modPow(u1, keys.p)).mod(keys.p).mod(keys.q);
		v = v.mod(keys.q);
		System.out.println("v:" + v);
		return v.equals(r);
	}

}
