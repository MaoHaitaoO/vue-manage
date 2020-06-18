package com.manage.core.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.manage.core.constant.ResultCodeEnum;
import com.manage.core.exception.RuleException;

@Component
public class RedisUtil {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 指定缓存失效时间
	 * 
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key, long time) {
		return redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * 根据key 获取过期时间
	 * 
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public boolean del(String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 普通缓存获取
	 * 
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 普通缓存放入并设置时间
	 * 
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public void set(String key, Object value, long time) {
		if (time < 0) {
			throw new RuleException(ResultCodeEnum.PARAM_ERROR);
		}
		redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
	}

	/**
	 * 递增
	 *
	 * @param key 键
	 * @return
	 */
	public long incr(String key) {
		return redisTemplate.opsForValue().increment(key);
	}

	/**
	 * 递减
	 *
	 * @param key 键
	 * @return
	 */
	public long decr(String key) {
		return redisTemplate.opsForValue().decrement(key);
	}

	/**
	 * 递增
	 * 
	 * @param key 键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuleException(ResultCodeEnum.PARAM_ERROR);
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 *
	 * @param key 键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuleException(ResultCodeEnum.PARAM_ERROR);
		}
		return redisTemplate.opsForValue().decrement(key, delta);
	}
}
