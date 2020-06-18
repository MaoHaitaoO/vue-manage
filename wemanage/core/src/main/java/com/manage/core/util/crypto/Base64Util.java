package com.manage.core.util.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.manage.core.constant.errorcode.CommonErrorEnum;
import com.manage.core.exception.RuleException;

public class Base64Util {

	public final static String ENCODING = "UTF-8";

	/**
	 * Base64编码
	 *
	 * @param data 待编码数据
	 * @return String 编码数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

	/**
	 * Base64安全编码<br>
	 * 遵循RFC 2045实现
	 *
	 * @param data 待编码数据
	 * @return String 编码数据
	 *
	 * @throws Exception
	 */
	public static String encodeSafe(String data) throws Exception {
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);
		return new String(b, ENCODING);
	}

	/**
	 * Base64解码
	 *
	 * @param data 待解码数据
	 * @return String 解码数据
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

	public static String imageToBase64(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		InputStream is = null;
		byte[] data;
		String result = null;
		try {
			is = new FileInputStream(path);
			data = new byte[is.available()];
			is.read(data);
			result = Base64.encodeBase64String(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
				}
			}

		}
		return result;
	}
}
