package com.manage.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.objenesis.ObjenesisStd;

import com.manage.core.constant.ResultCodeEnum;
import com.manage.core.exception.RuleException;

public class BeanCopierUtil {

	private BeanCopierUtil() {

	}

	private static ThreadLocal<ObjenesisStd> objenesisStdThreadLocal = ThreadLocal.withInitial(ObjenesisStd::new);
	private static ConcurrentHashMap<Class<?>, ConcurrentHashMap<Class<?>, BeanCopier>> cache =
			new ConcurrentHashMap<>();

	public static <T> T copy(Object source, Class<T> target) {
		return copy(source, objenesisStdThreadLocal.get().newInstance(target));
	}

	public static <T> T copy(Object source, T target) {
		BeanCopier beanCopier = getCacheBeanCopier(source.getClass(), target.getClass());
		beanCopier.copy(source, target, null);
		return target;
	}

	public static <T> List<T> copyList(List<?> sources, Class<T> target) {
		if (sources.isEmpty()) {
			return Collections.emptyList();
		}

		ArrayList<T> list = new ArrayList<>(sources.size());
		ObjenesisStd objenesisStd = objenesisStdThreadLocal.get();
		for (Object source : sources) {
			if (source == null) {
				throw new RuleException(ResultCodeEnum.UNKNOWN_ERROR);
			}
			T newInstance = objenesisStd.newInstance(target);
			BeanCopier beanCopier = getCacheBeanCopier(source.getClass(), target);
			beanCopier.copy(source, newInstance, null);
			list.add(newInstance);
		}
		return list;
	}

	public static <T> T mapToBean(Map<?, ?> source, Class<T> target) {
		T bean = objenesisStdThreadLocal.get().newInstance(target);
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(source);
		return bean;
	}

	public static <T> Map<?, ?> beanToMap(T source) {
		return BeanMap.create(source);
	}

	private static <S, T> BeanCopier getCacheBeanCopier(Class<S> source, Class<T> target) {
		ConcurrentHashMap<Class<?>, BeanCopier> copierConcurrentHashMap =
				cache.computeIfAbsent(source, aClass -> new ConcurrentHashMap<>(16));
		return copierConcurrentHashMap.computeIfAbsent(target, aClass -> BeanCopier.create(source, target, false));
	}
}
