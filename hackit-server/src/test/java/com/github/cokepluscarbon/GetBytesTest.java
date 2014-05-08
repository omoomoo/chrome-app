package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.BinaryCodec;
import org.junit.Test;

public class GetBytesTest {

	@Test
	public void t1() throws UnsupportedEncodingException {
		String src = "中国China";

		System.out.println(BinaryCodec.toAsciiString(src.getBytes()));
		System.out.println(BinaryCodec.toAsciiString(src.getBytes("GBK")));
		System.out.println(BinaryCodec.toAsciiString(src.getBytes("GB2312")));

		System.out.println();

		System.out.println(new String(src.getBytes()));
		System.out.println(new String(src.getBytes("GBK"), "GBK"));
		System.out.println(new String(src.getBytes("GB2312"), "GB2312"));
	}

	@Test
	public void t2() throws UnsupportedEncodingException {
		String src = "中国China";

		System.out.println(URLEncoder.encode(src, "UTF-8"));
		System.out.println(URLEncoder.encode(src, "GBK"));
		System.out.println(URLEncoder.encode(src, "GB2312"));
	}

	@Test
	public void t3() throws UnsupportedEncodingException {
		String src = "a";

		System.out.println(BinaryCodec.toAsciiString(src.getBytes()));
	}
}
