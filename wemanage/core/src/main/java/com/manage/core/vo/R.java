package com.manage.core.vo;

import com.manage.core.constant.ResultCodeEnum;

import lombok.Data;

@Data
public class R {

	private String code;

	private String message;

	// private Map<String, Object> data = new HashMap<>();

	private BaseDataRes data;


	// 构造器私有
	private R() {}

	// 通用返回成功
	public static R ok() {
		R r = new R();
		r.setCode(ResultCodeEnum.SUCCESS.getCode());
		r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
		return r;
	}

	// 通用返回失败，未知错误
	public static R error() {
		R r = new R();
		r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
		r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
		return r;
	}

	// 通用返回登录token失效，未知错误
	public static R invalid() {
		R r = new R();
		r.setCode(ResultCodeEnum.LOGIN_TOKEN_INVALID.getCode());
		r.setMessage(ResultCodeEnum.LOGIN_TOKEN_INVALID.getMessage());
		return r;
	}

	// 设置结果，形参为结果枚举
	public static R setResult(ResultCodeEnum result) {
		R r = new R();
		r.setCode(result.getCode());
		r.setMessage(result.getMessage());
		return r;
	}

	/** ------------使用链式编程，返回类本身----------- **/

	// 自定义返回数据
	// public R data(Map<String, Object> map) {
	// this.setData(map);
	// return this;
	// }

	// public R data(String json) {
	// this.setData(JSONObject.parseObject(json));
	// return this;
	// }

	public R data(BaseDataRes res) {
		this.setData(res);
		return this;
	}

	// 通用设置data
	// public R data(String key, Object value) {
	// this.data.put(key, value);
	// return this;
	// }

	// 自定义状态信息
	public R message(String message) {
		this.setMessage(message);
		return this;
	}

	// 自定义状态码
	public R code(String code) {
		this.setCode(code);
		return this;
	}
}
