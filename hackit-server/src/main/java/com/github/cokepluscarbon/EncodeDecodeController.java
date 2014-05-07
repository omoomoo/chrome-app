package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	private static final Logger logger = LoggerFactory.getLogger(EncodeDecodeController.class);

	@RequestMapping(value = "/encode/text", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, String> base64Encodexxx(String text, String encoding, HttpServletRequest request) {
		logger.info("页面编码：{}", request.getCharacterEncoding());
		logger.info("目标编码：{}", encoding);

		Map<String, String> rs = new HashMap<String, String>();
		try {
			byte[] bytes = new String(text.getBytes(), "UTF-8").getBytes();
			rs.put("plainText", text);
			rs.put("md5Text", md5Encode(bytes));
			rs.put("base64Text", base64Encode(bytes));
			rs.put("base64UrlSafeText", base64UrlEncode(bytes));
			rs.put("binaryText", binaryString(bytes));
			rs.put("hexText", hexString(bytes));
			rs.put("urlText", urlEncode(new String(text.getBytes(), "UTF-8"), encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

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
}
