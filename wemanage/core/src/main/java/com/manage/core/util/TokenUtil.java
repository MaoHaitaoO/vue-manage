package com.manage.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.manage.core.constant.errorcode.CommonErrorEnum;
import com.manage.core.exception.RuleException;

import sun.misc.BASE64Encoder;

public class TokenUtil {

	public static String createToken() {
		String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] = md.digest(token.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
		}
	}
}
