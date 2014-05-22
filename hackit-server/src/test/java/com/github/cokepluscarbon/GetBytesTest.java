package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class GetBytesTest {
	@Test
	public void t4() {
		String s = "曾";
		System.out.println(toHexString(s.getBytes()));
		System.out.println(Hex.encodeHexString(s.getBytes()));
	}

	public static String toHexString(byte[] data) {
		final int l = data.length;
		final char[] out = new char[l << 1];

		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_LOWER[(0x0F & data[i])];
		}

		return new String(out);
	}

	final static char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	@Test
	public void t5() {
		System.out.println(Byte.MIN_VALUE);
		System.out.println(Byte.MAX_VALUE);

		System.out.println(BinaryCodec.toAsciiString(new byte[] { Byte.MIN_VALUE }));
		System.out.println(BinaryCodec.toAsciiString(new byte[] { Byte.MAX_VALUE }));
	}

	@Test
	public void t6() {
		byte i = (byte) 0b10000000;
		byte j = (byte) 0b11111111;
		byte m = (byte) 0b00000000;
		byte n = (byte) 0b01111111;

		System.out.println(i);
		System.out.println(j);
		System.out.println(m);
		System.out.println(n);
	}

	@Test
	public void t7() {
		short i = (short) 0b1000000000000000;
		short j = (short) 0b1111111111111111;
		short m = (short) 0b0000000000000000;
		short n = (short) 0b0111111111111111;

		System.out.println(i);
		System.out.println(j);
		System.out.println(m);
		System.out.println(n);
	}

	@Test
	public void t8() {
		int i = (int) 0b10000000000000000000000000000000;
		int j = (int) 0b11111111111111111111111111111111;
		int m = (int) 0b00000000000000000000000000000000;
		int n = (int) 0b01111111111111111111111111111111;

		System.out.println(i); // -2147483648
		System.out.println(j); // -1
		System.out.println(m); // 0
		System.out.println(n); // 2147483647
	}

	@Test
	public void t9() {
		long i = (long) 0b1000000000000000000000000000000000000000000000000000000000000000L;
		long j = (long) 0b1111111111111111111111111111111111111111111111111111111111111111L;
		long m = (long) 0b0000000000000000000000000000000000000000000000000000000000000000L;
		long n = (long) 0b0111111111111111111111111111111111111111111111111111111111111111L;

		System.out.println(i); // -9223372036854775808
		System.out.println(j); // -1
		System.out.println(m); // 0
		System.out.println(n); // 9223372036854775807
	}

	@Test
	public void t10() throws UnsupportedEncodingException {

		char c = (char) 0b0100111000100101;
		System.out.println(c);
		System.out.println((short) c);

		System.out.println((char) 20005); // 严
	}

	@Test
	public void t11() throws UnsupportedEncodingException, DecoderException {
		Class c = Object.class;
		Class z = String.class;
		Class x = Test.class;
		Class v = BinaryCodec.class;

		Object o = null;
		o.getClass();

		Test test = null;
		test.getClass();
	}

	@Test
	public void t12_toAsciiString() throws UnsupportedEncodingException {
		byte[] data = new byte[] { (byte) 0xe0, 0x0f };
		byte[] dest = BinaryCodec.toAsciiBytes(data);
		ArrayUtils.reverse(dest);

		System.out.println(new String(dest, "ASCII"));
	}

	@Test
	public void t13_voidClass() {
		System.out.println(void.class);
		System.out.println(long.class);
	}
}
