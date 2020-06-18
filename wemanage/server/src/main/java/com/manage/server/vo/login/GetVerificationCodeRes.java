package com.manage.server.vo.login;

import com.manage.core.vo.BaseDataRes;

import lombok.Data;

@Data
public class GetVerificationCodeRes extends BaseDataRes {

	private String imgBase64;

	public GetVerificationCodeRes(String imgBase64){
		this.imgBase64 = imgBase64;
	}
}
