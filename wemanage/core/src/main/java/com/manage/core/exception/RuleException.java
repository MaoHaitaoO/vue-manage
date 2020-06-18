package com.manage.core.exception;

import com.manage.core.cache.ErrorCodeCache;
import com.manage.core.constant.ResultCodeEnum;
import com.manage.core.constant.errorcode.ErrCode;

import lombok.Data;

@Data
public class RuleException extends RuntimeException {

	private String code;

	public RuleException(ErrCode key, String message, Exception e) {
		super(getErrMsg(key) + message, e);
		this.code = getErrCode(key);
	}

	public RuleException(String code, String message) {
		super(message);
		this.code = code;
	}

	public RuleException(ErrCode key) {
		super(getErrMsg(key));
		this.code = getErrCode(key);
	}

	public RuleException(ResultCodeEnum resultCodeEnum) {
		super(resultCodeEnum.getMessage());
		this.code = resultCodeEnum.getCode();
	}

	@Override
	public String toString() {
		return "RuleException{" + "code=" + code + ", message=" + this.getMessage() + '}';
	}

	private static String getErrMsg(ErrCode key) {
		return ErrorCodeCache.get(key.toString()).getErrorMsg();
	}

	private static String getErrCode(ErrCode key) {
		return ErrorCodeCache.get(key.toString()).getErrorCode();
	}
}
