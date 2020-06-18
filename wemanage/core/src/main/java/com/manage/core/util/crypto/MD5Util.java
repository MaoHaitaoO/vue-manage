package com.manage.core.util.crypto;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	/**
	 * MD5加密
	 *
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 *
	 * @throws Exception
	 */
	public static byte[] encodeMD5(String data) throws Exception {
		return DigestUtils.md5(data);
	}

	/**
	 * MD5加密
	 *
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 *
	 * @throws Exception
	 */
	public static String encodeMD5Hex(String data) {
		return DigestUtils.md5Hex(data);
	}
}
