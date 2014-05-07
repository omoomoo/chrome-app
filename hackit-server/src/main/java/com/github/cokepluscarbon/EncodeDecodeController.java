package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EncodeDecodeController {
	private static final Logger logger = LoggerFactory
			.getLogger(EncodeDecodeController.class);

	@ResponseBody
	@RequestMapping(value = "/encode/text", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object base64Encodexxx(String text, String encoding,
			HttpServletRequest request) {
		logger.info("页面编码：{}", request.getCharacterEncoding());
		logger.info("目标编码：{}", encoding);

		Map<Object, Object> rs = new HashMap<Object, Object>();
		try {
			byte[] bytes = new String(text.getBytes(), "UTF-8").getBytes();
			rs.put("plainText", text);
			rs.put("md5Text", md5Encode(bytes));
			rs.put("base64Text", base64Encode(bytes));
			rs.put("base64UrlSafeText", base64UrlEncode(bytes));
			rs.put("binaryText", binaryString(bytes));
			rs.put("hexText", hexString(bytes));
			rs.put("urlText",
					urlEncode(new String(text.getBytes(), "UTF-8"), encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return rs;
	}

	@ResponseBody
	@RequestMapping(value = "/decode/text", params = "textType", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object decode(String text, String textType, String encoding,
			HttpServletRequest request) {
		logger.info("页面编码：{}", request.getCharacterEncoding());
		logger.info("目标编码：{}", encoding);

		byte[] bytes = null;
		if (textType.equals("base64Text")) {
			bytes = Base64.decodeBase64(text);
		} else if (textType.equals("base64UrlSafeText")) {
			bytes = Base64.decodeBase64(text);
		} else if (textType.equals("binaryText")) {
			bytes = BinaryCodec.fromAscii(text.toCharArray());
		} else if (textType.equals("urlText")) {
			try {
				bytes = URLDecoder.decode(text, text).getBytes();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (textType.equals("hexText")) {
			try {
				bytes = Hex.decodeHex(text.toCharArray());
			} catch (DecoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Map<String, String> rs = new HashMap<String, String>();

		return rs;
	}

	public String md5Encode(byte[] bytes) {
		return DigestUtils.md5DigestAsHex(bytes);
	}

	public String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public String base64UrlEncode(byte[] bytes) {
		return Base64.encodeBase64URLSafeString(bytes);
	}

	public String binaryString(byte[] bytes) {
		return BinaryCodec.toAsciiString(bytes);
	}

	public String hexString(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}

	public String urlEncode(String src, String encoding) {
		try {
			return URLEncoder.encode(src, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public byte[] decodeText(String text, String textType) {
		byte[] bytes = null;

		switch (textType) {
		case TextType.BASE64:
			bytes = DeEncodeUtils.decodeBase64(text);
			break;
		case TextType.BASE64_URL_SAFE:
			bytes = DeEncodeUtils.decodeBase64(text);
			break;
		case TextType.BINARY:
			bytes = DeEncodeUtils.decodeBinary(text);
			break;
		case TextType.HEX:
			bytes = DeEncodeUtils.decodeHex(text);
			break;
		case TextType.URL:
			bytes = DeEncodeUtils.decodeUrl("", "").getBytes();
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
