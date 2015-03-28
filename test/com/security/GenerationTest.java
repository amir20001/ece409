package com.security;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class GenerationTest {

	@Test
	public void test() {
		Generation test = new Generation();
		assertTrue(test.verifyValues());
	}

}
