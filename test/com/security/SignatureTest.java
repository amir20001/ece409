package com.security;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class SignatureTest {

	@Test
	public void test() throws Exception {
		Signature test = new Signature();
		BigInteger[] rs = test.generateSignature("testsetset");
		assertTrue(test.verifiySignature(rs[0], rs[1], "testsetset"));

	}

}
