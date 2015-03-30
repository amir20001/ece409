package com.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

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

}
