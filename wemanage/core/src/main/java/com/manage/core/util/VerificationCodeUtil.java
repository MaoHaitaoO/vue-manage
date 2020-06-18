package com.manage.core.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.Base64;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.manage.core.constant.ResultCodeEnum;
import com.manage.core.constant.TokenConstant;
import com.manage.core.exception.RuleException;

public class VerificationCodeUtil {
	/**
	 * 生成验证码图片
	 * 
	 * @param request 设置session
	 * @param response 转成图片
	 * @param captchaProducer 生成图片方法类
	 * @throws Exception
	 */
	public static String verificationCode(HttpServletRequest request, HttpServletResponse response,
			DefaultKaptcha captchaProducer) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();

		request.getSession().setAttribute(TokenConstant.LOGIN_VALIDATE_CODE_KEY, capText);
		request.getSession().setMaxInactiveInterval(60 * 2);

		BufferedImage bi = captchaProducer.createImage(capText);
		ByteArrayOutputStream out = null;
		String toBase64;
		try {
			out = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", out);
			toBase64 = Base64.byteArrayToBase64(out.toByteArray());
			// out.flush();
		} catch (Exception e) {
			throw new RuleException(ResultCodeEnum.VALIDATE_CODE_FAIL);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				out = null;
			}
		}
		return toBase64;
	}
}
