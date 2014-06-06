package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class EncodingTest {
	@Test
	public void t() throws UnsupportedEncodingException {
		try {
			System.out.println("try");
			return;
		} catch (Exception e) {

		} finally {
			System.out.println("finally");
		}
	}
}
