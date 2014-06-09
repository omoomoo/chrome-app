package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class EncodingTest {
	@Test
	public void t() throws UnsupportedEncodingException {
		byte[] bytes = new byte[] { -42, -48 }; // GBK编码的"中"字

		System.out.println(new String(bytes, "GBK")); // 正常

		String str1 = new String(bytes, "UTF-8");
		System.out.println(str1); // 乱码

		String str2 = new String(str1.getBytes("UTF-8"), "GBK");
		System.out.println(str2); // 打印正常否？
		
		String str3 = new String(str1.getBytes("GBK"), "UTF-8");
		System.out.println(str3); // 打印正常否？
		
		String str4 = new String(str1.getBytes("UTF-8"), "UTF-8");
		System.out.println(str4); // 打印正常否？
		
		String str5 = new String(str1.getBytes("GBK"), "GBK");
		System.out.println(str5); // 打印正常否？
		
		String str6 = new String(str1.getBytes("UTF-8"), "ISO-8859-1");
		System.out.println(str6); // 打印正常否？
	}
	
}
