package com.manage.server.service.impl;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.core.cache.SystemConfigCache;
import com.manage.core.constant.CommonConstant;
import com.manage.core.constant.SystemConfigKey;
import com.manage.core.constant.TokenConstant;
import com.manage.core.constant.UserStatus;
import com.manage.core.constant.errorcode.UserErrorEnum;
import com.manage.core.po.User;
import com.manage.core.util.RedisUtil;
import com.manage.core.util.TokenUtil;
import com.manage.core.util.crypto.RSAUtil;
import com.manage.core.validate.Validate;
import com.manage.server.dao.UserDao;
import com.manage.server.service.LoginService;
import com.manage.server.vo.login.LoginReq;
import com.manage.server.vo.login.LoginRes;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public LoginRes login(LoginReq req, HttpSession session) {
		validateVerificationCode(req, session);
		User user = userDao.selectByUsername(req.getUsername());
		validateUser(user, req);

		shiro(user);

		return setToken(user);
	}

	private void validateUser(User user, LoginReq req) {
		Validate.assertNotNull(user, UserErrorEnum.USER_NOT_EXIST);
		Validate.assertTrue(StringUtils.equals(user.getStatus(), UserStatus.USER_STATUS_OK),
				UserErrorEnum.USER_STATUS_IS_OFF);
		validatePwd(user, req);
	}

	private void validatePwd(User user, LoginReq req) {
		String reqPwdDecrypt =
				RSAUtil.decrypt(req.getPassword(), SystemConfigCache.get(SystemConfigKey.LOGIN_PRIVATE_KEY));
		String userPwdDecrypt =
				RSAUtil.decrypt(user.getUserPwd(), SystemConfigCache.get(SystemConfigKey.LOGIN_PRIVATE_KEY));
		Validate.assertTrue(StringUtils.equals(reqPwdDecrypt, userPwdDecrypt), UserErrorEnum.USER_PASSWORD_ERROR);
	}

	private void validateVerificationCode(LoginReq req, HttpSession session) {
		if (StringUtils.equals(SystemConfigCache.get(SystemConfigKey.LOGIN_VERIFICATION_CODE_SWITCH),
				CommonConstant.SWITCH_OFF)) {
			return;
		}
		Object verificationCode = session.getAttribute(TokenConstant.LOGIN_VALIDATE_CODE_KEY);
		Validate.assertNotNull(verificationCode, UserErrorEnum.VERIFICATION_CODE_INVALID);
		System.out.println(verificationCode.toString());
		Validate.assertTrue(StringUtils.equalsIgnoreCase(String.valueOf(verificationCode), req.getVerificationCode()),
				UserErrorEnum.VERIFICATION_CODE_ERROR);
	}

	private LoginRes setToken(User user) {
		String token = TokenConstant.TOKEN_LOGIN_PREFIX + TokenUtil.createToken();
		long time = SystemConfigCache.getInt(SystemConfigKey.LOGIN_TOKEN_TIME);
		redisUtil.set(token, user, time);

		LoginRes res = new LoginRes();
		res.setToken(token);
		res.setNickName(user.getNickName());
		return res;
	}

	private void shiro(User user){
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getUserPwd());
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);
	}
}
