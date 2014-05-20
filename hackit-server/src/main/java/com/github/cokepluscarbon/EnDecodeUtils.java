package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
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
		byte[] copyBytes = bytes.clone();
		ArrayUtils.reverse(copyBytes);

		return BinaryCodec.toAsciiString(copyBytes);
	}

	public static String encodeHex(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}

	public static String encodeUrl(byte[] bytes, String encoding) {
		try {
			return URLEncoder.encode(new String(bytes, encoding), encoding);
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

	public static byte[] decodeUrl(String text, String encoding) throws UnsupportedEncodingException {
		return URLDecoder.decode(text, encoding).getBytes(encoding);
	}

	public static byte[] decodeBinary(String text) {
		byte[] dest = BinaryCodec.fromAscii(text.toCharArray());
		ArrayUtils.reverse(dest);

		return dest;
	}

	public static byte[] decodeHex(String text) throws DecoderException {
		return Hex.decodeHex(text.toCharArray());
	}

	public static byte[] decodeBase64(String text) {
		return Base64.decodeBase64(text);
	}

	public static byte[] getBytes(String text, String textType, String encoding) throws UnsupportedEncodingException,
			DecoderException {
		byte[] bytes = null;

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
			bytes = EnDecodeUtils.decodeUrl(text, encoding);
			break;
		default:
			break;
		}

		return bytes;
	}

}
