package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.DigestUtils;

public class DeEncodeUtils {
	public static String encodeMd5(byte[] bytes) {
		return DigestUtils.md5DigestAsHex(bytes);
	}

	public static String encodeBase64(byte[] bytes, boolean isUrlSafe) {
		if (isUrlSafe) {
			return Base64.encodeBase64URLSafeString(bytes);
		} else {
			return Base64.encodeBase64String(bytes);
		}
	}

	public static String encodeBinary(byte[] bytes) {
		return BinaryCodec.toAsciiString(bytes);
	}

	public static String encodeHex(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}

	public static String encodeUrl(String src, String encoding) {
		try {
			return URLEncoder.encode(src, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String decodeUrl(String src, String encoding) {
		try {
			return URLDecoder.decode(src, encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decodeBinary(String text) {
		return BinaryCodec.fromAscii(text.toCharArray());
	}

	public static byte[] decodeHex(String text) {
		try {
			return Hex.decodeHex(text.toCharArray());
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decodeBase64(String text) {
		return Base64.decodeBase64(text);
	}

}
