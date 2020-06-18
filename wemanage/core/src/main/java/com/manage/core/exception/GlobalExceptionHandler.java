package com.manage.core.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import com.manage.core.constant.ResultCodeEnum;
import com.manage.core.vo.R;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	public R error(HttpClientErrorException e) {
		log.error(e.getMessage(), e);
		return R.setResult(ResultCodeEnum.HTTP_CLIENT_ERROR);
	}

	@ExceptionHandler(RuleException.class)
	@ResponseBody
	public R error(RuleException e) {
		log.error("[错误码：" + e.getCode() + " , 错误原因：" + e.getMessage() + "]", e);
		return R.error().message(e.getMessage()).code(e.getCode());
	}

	@ExceptionHandler(BindException.class)
	@ResponseBody
	public R error(BindException e) {
		log.error(e.getMessage(), e);
		return R.setResult(ResultCodeEnum.PARAM_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R error(Exception e) {
		log.error(e.getMessage(), e);
		return R.error();
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public R error(UnauthorizedException e) {
		log.error(e.getMessage(), e);
		return R.setResult(ResultCodeEnum.NO_ACCESS);
	}

}
