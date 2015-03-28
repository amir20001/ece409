package com.security;

import static org.junit.Assert.*;

import org.junit.Test;

public class SignatureTest {

	@Test
	public void test() {
		Signature test = new Signature();
		test.generateSignature("testsetset");
	}

}
