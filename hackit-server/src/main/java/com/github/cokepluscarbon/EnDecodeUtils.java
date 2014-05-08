package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.DigestUtils;

public class EnDecodeUtils {
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

	public static String encodeUrl(byte[] bytes, String encoding) {
		try {
			return URLEncoder.encode(encodePlain(bytes, encoding), encoding);
		} catch (UnsupportedEncodingException e) {
			return String.format("encoding:%s not spoort!", encoding);
		}
	}

	public static String encodePlain(byte[] bytes, String encoding) {
		try {
			return new String(bytes, encoding);
		} catch (UnsupportedEncodingException e) {
			return String.format("encoding:%s not spoort!", encoding);
		}
	}

	public static String decodeUrl(String src, String encoding) throws UnsupportedEncodingException {
		return URLDecoder.decode(src, encoding);
	}

	public static byte[] decodeBinary(String text) {
		return BinaryCodec.fromAscii(text.toCharArray());
	}

	public static byte[] decodeHex(String text) throws DecoderException {
		return Hex.decodeHex(text.toCharArray());
	}

	public static byte[] decodeBase64(String text) {
		return Base64.decodeBase64(text);
	}

	public static byte[] getBytes(String src, String textType, String encoding) throws UnsupportedEncodingException,
			DecoderException {
		byte[] bytes = null;
		String text = new String(src.getBytes(), encoding);

		switch (textType) {
		case TextType.BASE64:
			bytes = EnDecodeUtils.decodeBase64(text);
			break;
		case TextType.BASE64_URL_SAFE:
			bytes = EnDecodeUtils.decodeBase64(text);
			break;
		case TextType.BINARY:
			bytes = EnDecodeUtils.decodeBinary(text);
			break;
		case TextType.HEX:
			bytes = EnDecodeUtils.decodeHex(text);
			break;
		case TextType.URL:
			bytes = EnDecodeUtils.decodeUrl(src, encoding).getBytes();
			break;
		case TextType.PLAIN:
			bytes = text.getBytes();
			break;
		default:
			break;
		}

		return bytes;
	}

}
