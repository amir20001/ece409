package com.security;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signature {
	private Generation keys;

	public Signature() {
		keys = new Generation();
	}

	protected BigInteger[] generateSignature(String message) throws NoSuchAlgorithmException {
		BigInteger result[] = new BigInteger[2];
		BigInteger k = (new BigDecimal(Math.random()).multiply(new BigDecimal(keys.getQ().toString()))).toBigInteger();
		BigInteger kInverse = k.modInverse(keys.getQ());
		BigInteger r = keys.getG().modPow(k, keys.getP()).mod(keys.getQ());
		BigInteger sha1Value = sha1Message(message);
		BigInteger s = keys.getPrivateKey().multiply(r).add(sha1Value).multiply(kInverse).mod(keys.getQ());
		System.out.println("k:" + k);
		System.out.println("kInv:" + kInverse);
		System.out.println("r:" + r);
		System.out.println("s:" + s);
		result[0] = s;
		result[1] = r;
		if (r.equals(BigInteger.ZERO) || s.equals(BigInteger.ZERO))
			return generateSignature(message);
		else
			return result;
	}

	protected boolean verifiySignature(BigInteger s, BigInteger r, String message) {
		if (Util.isLessThan(r, BigInteger.ZERO) || Util.isGreaterThan(r, keys.getQ()))
			return false;
		BigInteger w = s.modInverse(keys.getQ());
		BigInteger u1 = w.multiply(sha1Message(message)).mod(keys.getQ());
		BigInteger u2 = w.multiply(r).mod(keys.getQ());
		BigInteger v = keys.getG().modPow(u1, keys.getQ()).multiply((keys.getPublicKey().modPow(u2, keys.getP()).mod(keys.getQ())));
		v = v.mod(keys.getQ());
		System.out.println("v:" + v);
		return v.equals(r);
	}

	private BigInteger sha1Message(String s) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			sha1.update(s.getBytes());
			BigInteger sha1Value = new BigInteger(sha1.digest());
			return sha1Value;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("sha algo not found");
		}
	}
}
