package com.github.cokepluscarbon;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EnDecodeController {
	private static final Logger logger = LoggerFactory.getLogger(EnDecodeController.class);

	@ResponseBody
	@RequestMapping(value = "/encode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object base64Encodexxx(String text, String encoding, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		if (!checkEncoding(encoding)) {
			String message = String.format("不支持的编码格式：%s", encoding);
			logger.error(message);
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return message;
		}

		// byte[] bytes = new String(text.getBytes(),
		// response.getCharacterEncoding()).getBytes(encoding);
		byte[] bytes = text.getBytes(encoding);

		Map<Object, Object> rs = new HashMap<Object, Object>();
		rs.put(TextType.PLAIN, text);
		rs.put(TextType.MD5, EnDecodeUtils.encodeMd5(bytes));
		rs.put(TextType.BASE64, EnDecodeUtils.encodeBase64(bytes, false));
		rs.put(TextType.BASE64_URL_SAFE, EnDecodeUtils.encodeBase64(bytes, true));
		rs.put(TextType.BINARY, EnDecodeUtils.encodeBinary(bytes));
		rs.put(TextType.HEX, EnDecodeUtils.encodeHex(bytes));
		rs.put(TextType.URL, EnDecodeUtils.encodeUrl(bytes, encoding));

		return rs;
	}

	@ResponseBody
	@RequestMapping(value = "/decode", params = "textType", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object decode(String text, String textType, String encoding, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		if (!checkEncoding(encoding)) {
			String message = String.format("不支持的编码格式：%s", encoding);
			logger.error(message);
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return message;
		}

		byte[] bytes = null;
		try {
			bytes = EnDecodeUtils.getBytes(text, textType, encoding);
		} catch (UnsupportedEncodingException e) {
			String message = String.format("不支持的编码格式：%s", encoding);
			logger.error(message);
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return message;
		} catch (DecoderException e) {
			String message = String.format("数据转换出现异常：%s", e.getMessage());
			logger.error(message);
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return message;
		} catch (Exception e) {
			String message = String.format("系统处理出现异常：%s", e.getMessage());
			logger.error(message);
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return message;
		}

		Map<String, String> rs = new HashMap<String, String>();
		rs.put(TextType.PLAIN, new String(bytes, encoding));
		rs.put(TextType.MD5, EnDecodeUtils.encodeMd5(bytes));
		rs.put(TextType.BASE64, EnDecodeUtils.encodeBase64(bytes, false));
		rs.put(TextType.BASE64_URL_SAFE, EnDecodeUtils.encodeBase64(bytes, true));
		rs.put(TextType.BINARY, EnDecodeUtils.encodeBinary(bytes));
		rs.put(TextType.HEX, EnDecodeUtils.encodeHex(bytes));
		rs.put(TextType.URL, EnDecodeUtils.encodeUrl(bytes, encoding));

		return rs;
	}

	public boolean checkEncoding(String encoding) {
		return encoding.matches("GBK|GB2312|GB18030|UTF-8|UTF-16|UTF-32|BIG5|ASCII|Shift_JIS|ISO/IEC 2022|EUC-JP|EUC-TW|ISCII");
	}

}
