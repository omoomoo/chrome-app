package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import org.junit.Test;

public class Base64Test {

	@Test
	public void t1_encode() throws UnsupportedEncodingException {
		String text = "HelloWorld!";
		System.out.println(base64Encode(text.getBytes("ASCII"))); // SGVsbG9Xb3JsZCE=
	}

	public static String base64Encode(byte[] data) {
		int len = data.length;
		int paddingLen = 3 - (len % 3 == 0 ? 3 : len % 3); // 填充字节数

		StringBuilder dest = new StringBuilder();
		for (int i = 0; i < len - paddingLen - 1;) {
			handleUnit(data[i++], data[i++], data[i++], dest);
		}
		if (paddingLen != 0) { // 处理填充
			handlePadding(data, len, paddingLen, dest);
		}

		return dest.toString();
	}

	/**
	 * Base64编码处理单元
	 */
	public static void handleUnit(byte b1, byte b2, byte b3, StringBuilder dest) {
		dest.append(BASE64_DIGITS[b1 >>> 2]);
		dest.append(BASE64_DIGITS[(b1 << 4 | b2 >>> 4) & 0x3f]);
		dest.append(BASE64_DIGITS[(b2 << 2 | b3 >>> 6) & 0x3f]);
		dest.append(BASE64_DIGITS[b3 & 0x3f]);
	}

	public static void handlePadding(byte[] data, int len, int paddingLen, StringBuilder dest) {
		if (paddingLen == 1) {
			handleUnit(data[len - len % 3], data[len - len % 3 + 1], (byte) 0x00, dest);
		}

		if (paddingLen == 2) {
			handleUnit(data[len - len % 3], (byte) 0x00, (byte) 0x00, dest);
		}

		dest.setLength(dest.length() - paddingLen); // 删除由0填充产生的字符

		for (int j = 0; j < paddingLen; j++) { // 填充"="
			dest.append("=");
		}
	}

	private static final char[] BASE64_DIGITS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/' };
}
