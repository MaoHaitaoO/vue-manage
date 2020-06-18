package com.manage.core.util.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import com.manage.core.constant.errorcode.CommonErrorEnum;
import com.manage.core.exception.RuleException;

public class RSAUtil {

	private static final String PUBLIC_KEY = "publicKey";

	private static final String PRIVATE_KEY = "privateKey";

	private static Map<String, String> keyMap = new HashMap<>();

	public static void main(String[] args) throws Exception {
		// 随机生成的公钥为:MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCP9VUkxat1YWBkpp1Q4/wETIPH2AtNWhBYfGmMWwrSjscqTrAHcQN01NShwQcdyYIZ5UrRNuBb5DOajSbhFQi5W3hdW+pcOBST3JS44xh31Krp6dVhcW/71zD1UVdWIkLR5qRm24Xhwou8RrO71lof70bwdHdBhaQid6udyxKbNQIDAQAB
		// 随机生成的私钥为:MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI/1VSTFq3VhYGSmnVDj/ARMg8fYC01aEFh8aYxbCtKOxypOsAdxA3TU1KHBBx3JghnlStE24FvkM5qNJuEVCLlbeF1b6lw4FJPclLjjGHfUqunp1WFxb/vXMPVRV1YiQtHmpGbbheHCi7xGs7vWWh/vRvB0d0GFpCJ3q53LEps1AgMBAAECgYBTrMkBp7fCcYhC2KmGFA4vPX43kbb4DgqENERxZZXDz2OqjrQ4jxLwPKkfIACP6smpXaKAOsJsBxeLlNHtcHlxyJJ7My9+XCynYrBdhSI+gqNnHMCpU8rqe4ol5d2fWkldyLqYHzqOI70MJpeII/AajqDaL+Fq5cViu9X6hcyKAQJBAOibtRDoQTf3Ig9Zcu1j5ff2ED5398JqjtEI7eiEIpWk124SOZC9n9NhqGBanbxU/WItGaSbxV0rXIy87ggCOrUCQQCeb2mFINWMImTJ6Bfy2BN1gja5xKoYedrYfmc6kKXX+YsWJfWbrYV3K6M6Y4d+k2Ahx7sGYDiTl8w7vz6xXK6BAkEAjXLqZ66kwlCxUJ43ai5+kQv8EtDHRF2iHXGNowB/O3qzWocQpHj7cQKI7pcMJpOURhhg6J2lJiQRMrBNG2enpQJAckPkFJHDshe0HTJ44zoXfq51DbHbA2APCAjSJpjgI03klAq4gpi5I2Oji57wv8dGk9Za+pQ2+auHLqgVbRFtAQJBAI4DVfZJGP2Z2DQkWouBOkPvKOh5J6zqSsCkSW+PDPyyiPB+HnYxf/j/5OTA8tmS79rmNZe+C7bAlJzVb4vBirA=
		// mht
		// 加密后的字符串为:Eu7VQgu7mb0XmE4LJ5oNhQrKiCK+JGeVSbIiWxJVxNQPEmgv3dlLzO1xrIAyMQDMRW5OEunXsFgamkEoB4j7Y5kbJir6SMBXpRbpt8V4nf33DbHqJzY6eeZvT7FUM2iuW2JfoYh2GsVbx6NC4QPgAycQeM1grFZhBZEI1vOR3C8=
		// 还原后的字符串为:mht

		String jiami =
				"XfqE/B+AepYnOMUTm7eDHEWMZAg+omxuD0hXwcVelxHUnMvih+wCqvYWQsA4OXraYx9reXmO3f9xLduyPpU2DdEmmC8gBPoA8NzGUJ+YMLrEzo2j98reVRjzHs1wSKQQ3WDrfWRF2ki2fyzBBKZUZV+7RNAGcj16ECm+SRUAHrw=";

		String privateKey =
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI/1VSTFq3VhYGSmnVDj/ARMg8fYC01aEFh8aYxbCtKOxypOsAdxA3TU1KHBBx3JghnlStE24FvkM5qNJuEVCLlbeF1b6lw4FJPclLjjGHfUqunp1WFxb/vXMPVRV1YiQtHmpGbbheHCi7xGs7vWWh/vRvB0d0GFpCJ3q53LEps1AgMBAAECgYBTrMkBp7fCcYhC2KmGFA4vPX43kbb4DgqENERxZZXDz2OqjrQ4jxLwPKkfIACP6smpXaKAOsJsBxeLlNHtcHlxyJJ7My9+XCynYrBdhSI+gqNnHMCpU8rqe4ol5d2fWkldyLqYHzqOI70MJpeII/AajqDaL+Fq5cViu9X6hcyKAQJBAOibtRDoQTf3Ig9Zcu1j5ff2ED5398JqjtEI7eiEIpWk124SOZC9n9NhqGBanbxU/WItGaSbxV0rXIy87ggCOrUCQQCeb2mFINWMImTJ6Bfy2BN1gja5xKoYedrYfmc6kKXX+YsWJfWbrYV3K6M6Y4d+k2Ahx7sGYDiTl8w7vz6xXK6BAkEAjXLqZ66kwlCxUJ43ai5+kQv8EtDHRF2iHXGNowB/O3qzWocQpHj7cQKI7pcMJpOURhhg6J2lJiQRMrBNG2enpQJAckPkFJHDshe0HTJ44zoXfq51DbHbA2APCAjSJpjgI03klAq4gpi5I2Oji57wv8dGk9Za+pQ2+auHLqgVbRFtAQJBAI4DVfZJGP2Z2DQkWouBOkPvKOh5J6zqSsCkSW+PDPyyiPB+HnYxf/j/5OTA8tmS79rmNZe+C7bAlJzVb4vBirA=";
		String messageDe = decrypt(jiami, privateKey);
		System.out.println("还原后的字符串为:" + messageDe);
		// 生成公钥和私钥
		// genKeyPair();
		// String message = "mht";
		// System.out.println("随机生成的公钥为:" + keyMap.get(PUBLIC_KEY));
		// System.out.println("随机生成的私钥为:" + keyMap.get(PRIVATE_KEY));
		// String messageEn = encrypt(message, keyMap.get(PUBLIC_KEY));
		// System.out.println(message + "\t加密后的字符串为:" + messageEn);
		// String messageDe = decrypt(messageEn, keyMap.get(PRIVATE_KEY));
		// System.out.println("还原后的字符串为:" + messageDe);
	}

	private static void genKeyPair() throws NoSuchAlgorithmException {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024, new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
		// 得到私钥字符串
		String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
		// 将公钥和私钥保存到Map
		keyMap.put(PUBLIC_KEY, publicKeyString);
		keyMap.put(PRIVATE_KEY, privateKeyString);
	}

	public static String encrypt(String str, String publicKey) {
		String outStr;
		try {
			// base64编码的公钥
			byte[] decoded = Base64.decodeBase64(publicKey);
			RSAPublicKey pubKey =
					(RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
			// RSA加密
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
		}
		return outStr;
	}

	public static String decrypt(String str, String privateKey) {
		String outStr;
		try {
			// 64位解码加密后的字符串
			byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
			// base64编码的私钥
			byte[] decoded = Base64.decodeBase64(privateKey);
			RSAPrivateKey priKey =
					(RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
			// RSA解密
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			outStr = new String(cipher.doFinal(inputByte));
		} catch (Exception e) {
			throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
		}
		return outStr;
	}

}
