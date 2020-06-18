package com.manage.server.service;

import javax.servlet.http.HttpSession;

import com.manage.server.vo.login.LoginReq;
import com.manage.server.vo.login.LoginRes;

public interface LoginService {

	LoginRes login(LoginReq req, HttpSession session);


}
