package com.manage.core.constant;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
	SUCCESS("200","成功"),
	UNKNOWN_ERROR("400","系统错误"),
	PARAM_ERROR("500","参数错误"),
	HTTP_CLIENT_ERROR("600","网络异常"),
	LOGIN_TOKEN_INVALID("401","token失效,请重新登录"),
	VALIDATE_CODE_FAIL("402","获取验证码失败，请重新获取"),
	NO_ACCESS("403","没有权限访问");


	private String code;

	private String message;

	ResultCodeEnum( String code, String message) {
		this.code = code;
		this.message = message;
	}
}
