package com.manage.core.validate;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.manage.core.constant.errorcode.ErrCode;
import com.manage.core.exception.RuleException;

public class Validate {

	public static void assertNotBlank(String param, ErrCode code) {
		if (StringUtils.isBlank(param)) {
			throw new RuleException(code);
		}
	}

	public static void assertBlank(String param, ErrCode code) {
		if (StringUtils.isNotBlank(param)) {
			throw new RuleException(code);
		}
	}

	public static void assertNotEmpty(Map param, ErrCode code) {
		if (CollectionUtils.isEmpty(param)) {
			throw new RuleException(code);
		}
	}

	public static void assertNotEmpty(Collection list, ErrCode code) {
		if (CollectionUtils.isEmpty(list)) {
			throw new RuleException(code);
		}
	}

	public static void assertEmpty(Map<String, Object> param, ErrCode code) {
		if (!CollectionUtils.isEmpty(param)) {
			throw new RuleException(code);
		}
	}

	public static void assertNotNull(Object param, ErrCode code) {
		if (param == null) {
			throw new RuleException(code);
		}
	}

	public static void assertNull(Object param, ErrCode code) {
		if (param != null) {
			throw new RuleException(code);
		}
	}

	public static void assertTrue(Boolean param, ErrCode code) {
		if (!param) {
			throw new RuleException(code);
		}
	}

	public static void assertFalse(Boolean param, ErrCode code) {
		if (param) {
			throw new RuleException(code);
		}
	}

	public static void assertMatch(String param, String pattern, ErrCode code) {
		assertTrue(param.matches(pattern), code);
	}
}
