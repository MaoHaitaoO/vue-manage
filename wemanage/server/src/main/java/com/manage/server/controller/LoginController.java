package com.manage.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.manage.core.util.VerificationCodeUtil;
import com.manage.core.vo.R;
import com.manage.server.service.LoginService;
import com.manage.server.vo.login.GetVerificationCodeRes;
import com.manage.server.vo.login.LoginReq;
import com.manage.server.vo.login.LoginRes;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@PostMapping("login")
	public R login(@Valid LoginReq req, HttpSession session) {
		LoginRes res = loginService.login(req, session);
		return R.ok().data(res);
	}

	@GetMapping("getVerificationCode")
	public R getVerificationCode(HttpServletRequest request, HttpServletResponse response) {
		String imgToBase64 = VerificationCodeUtil.verificationCode(request, response, defaultKaptcha);
		return R.ok().data(new GetVerificationCodeRes(imgToBase64));
	}

	@PostMapping("logout")
	public R logout(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("token");
		if (request.getSession().getAttribute(token) != null) {
			request.getSession().removeAttribute(token);
		}

		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return R.ok();
	}
}
