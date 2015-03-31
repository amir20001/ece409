package com.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static final int PORT = 10101;

	public static boolean isGreaterThan(BigInteger a, BigInteger b) {
		return a.compareTo(b) == 1;

	}

	public static boolean isLessThan(BigInteger a, BigInteger b) {
		return a.compareTo(b) == -1;
	}

	public static String readLine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println("IO error trying to read your name!");
			System.exit(1);
		}
		return null;
	}

	public static String readFromConnection(Socket conn) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		return in.readLine();
	}

	public static void sendOverConnection(Socket conn, String data) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bw.write(data);
		bw.newLine();
		bw.flush();

	}

	public static BigInteger sha1Message(String s) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			sha1.update(s.getBytes());
			BigInteger sha1Value = new BigInteger(sha1.digest());
			return sha1Value;
		} catch (NoSuchAlgorithmException e) {
			// should never happen
			throw new RuntimeException("sha algo not found");
		}
	}

}
