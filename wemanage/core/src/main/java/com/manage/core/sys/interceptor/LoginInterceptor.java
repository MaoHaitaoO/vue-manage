package com.manage.core.sys.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.manage.core.cache.SystemConfigCache;
import com.manage.core.constant.SystemConfigKey;
import com.manage.core.po.User;
import com.manage.core.util.RedisUtil;
import com.manage.core.vo.R;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			printRes(response, R.invalid());
			return false;
		}

		RedisUtil redisUtil = getBean(RedisUtil.class, request);
		User user = (User) redisUtil.get(token);
		if (user != null) {
			long time = SystemConfigCache.getInt(SystemConfigKey.LOGIN_TOKEN_TIME);
			redisUtil.expire(token, time);
			return true;
		}

		printRes(response, R.invalid());
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private <T> T getBean(Class<T> clazz, HttpServletRequest request) {
		// 通过该方法获得的applicationContext 已经是初始化之后的applicationContext 容器
		WebApplicationContext applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return applicationContext.getBean(clazz);
	}

	private void printRes(HttpServletResponse response, R r) throws Exception {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(JSONObject.toJSONString(r));
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
