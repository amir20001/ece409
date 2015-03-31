package com.security;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
	private static final String PATTERN = "^(\\d){4}-(\\d){2}-(\\d){2}$";
	private static BigInteger privateKey = new BigInteger("432398415306986194693973996870836079581453988813");

	public static void main(String[] args) {
		try {
			System.out.println("Waiting for user request...");
			ServerSocket serverSocket = new ServerSocket(Util.PORT);
			Socket connection = serverSocket.accept();
			String data = Util.readFromConnection(connection);
			String[] tokens = data.split(",");
			String identity = tokens[0];
			String userPublicKey = tokens[1];
			String expiryDate = getExpiry();
			String message = new StringBuilder().append(identity).append(userPublicKey).append(expiryDate).toString();
			BigInteger[] rs = generateSignature(message);
			System.out.println("The user " + identity + " with IP address /"
					+ connection.getRemoteSocketAddress().toString() + " holds the following mini-certificate:");
			System.out.println(message);
			System.out.println(rs[1]);
			System.out.println(rs[0]);
			String response = new StringBuilder().append(identity).append(",").append(userPublicKey).append(",")
					.append(expiryDate).append(",").append(rs[0]).append(",").append(rs[1]).toString();
			Util.sendOverConnection(connection, response);

			connection.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getExpiry() {
		String date = "";
		while (!validateDate(date)) {
			System.out.println("Enter the expiry date of the certificate (yyyy-mm-dd):");
			date = Util.readLine();
		}
		return date;
	}

	private static boolean validateDate(final String date) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(date);
		return matcher.matches();
	}

	private static BigInteger[] generateSignature(String message) {
		PublicKeys keys = PublicKeys.getInstance();
		keys.verifyValues();
		keys.verifyPrivateKeys(privateKey);
		BigInteger result[] = new BigInteger[2];
		BigInteger k = (new BigDecimal(Math.random()).multiply(new BigDecimal(keys.q.toString()))).toBigInteger();
		BigInteger kInverse = k.modInverse(keys.q);
		BigInteger r = keys.g.modPow(k, keys.p).mod(keys.q);
		BigInteger sha1Value = Util.sha1Message(message);

		BigInteger s = privateKey.multiply(r).add(sha1Value).multiply(kInverse).mod(keys.q);
		result[0] = s;
		result[1] = r;
		if (r.equals(BigInteger.ZERO) || s.equals(BigInteger.ZERO))
			return generateSignature(message);
		else
			return result;
	}

}
