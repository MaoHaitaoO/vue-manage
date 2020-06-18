package com.manage.core.cache;

/**
 * 缓存基类，除错误码缓存和参数缓存不继承此类，其余缓存类都需继承此类
 */
public abstract class BaseCacheAbstract<T> extends BaseSysCacheAbstract{

	protected abstract T get(String key);

	protected int getInt(String key) {
		String value = (String) get(key);
		return Integer.parseInt(value);
	}
}
