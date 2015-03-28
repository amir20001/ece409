package com.security;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Signature {
	private Generation keys;

	public Signature() {
		keys = new Generation();
	}

	public String generateSignature(String message) {
		double rand = Math.random();
		rand = rand * keys.getQ().doubleValue();
		BigInteger k = new BigInteger(Long.valueOf(Math.round(rand)).toString());
		BigInteger kInverse = k.modInverse(keys.getQ());

		System.out.println("k: " + k);
		System.out.println("kInverse: "+kInverse);
		return null;
	}

}
