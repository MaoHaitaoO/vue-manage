package com.manage.server.vo.login;

import com.manage.core.vo.BaseDataRes;

import lombok.Data;

@Data
public class LoginRes extends BaseDataRes {

	private String token;

	private String nickName;
}
