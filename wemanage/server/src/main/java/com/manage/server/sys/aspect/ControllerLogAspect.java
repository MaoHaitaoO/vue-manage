package com.manage.server.sys.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class ControllerLogAspect {

	@Pointcut("execution(public * com.manage.server.controller..*.*(..))")
	public void pointcut() {}

	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		String methodName = methodSignature.getMethod().getName();
		StringBuilder sb = new StringBuilder();
		sb.append(methodName + "接口请求--->");
		sb.append(",参数[");
		for (Object arg : args) {
			sb.append(arg.toString() + ",");
		}
		sb.append("]");
		log.info(sb.toString());
	}

	@AfterReturning(pointcut = "pointcut()", returning = "ret")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		String methodName = methodSignature.getMethod().getName();
		log.info(methodName + "接口返回<---" + JSON.toJSONString(ret));
	}
}
