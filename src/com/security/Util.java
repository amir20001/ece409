package com.security;

import java.math.BigInteger;

public class Util {
	public static boolean isGreaterThan(BigInteger a, BigInteger b) {
		return a.compareTo(b) == 1;

	}

	public static boolean isLessThan(BigInteger a, BigInteger b) {
		return a.compareTo(b) == -1;
	}

}
