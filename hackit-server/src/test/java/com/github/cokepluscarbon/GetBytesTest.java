package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
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

	@Test
	public void t3_htmlEntity() throws UnsupportedEncodingException {
		System.out.println(StringEscapeUtils.escapeHtml4("曾<tiger>"));
		System.out.println(StringEscapeUtils.unescapeHtml4("&#x6A;&#x61;&#x76;&#x61;&#x73;&#x63;&#x72;&#x69;&#x70;&#x74;&#x3A;&#x61;&#x6C;&#x65;&#x72;&#x74;&#x28;&#x27;&#x78;&#x73;&#x73;&#x27;&#x29;&#x3B;"));
		
	}
	
	
}
