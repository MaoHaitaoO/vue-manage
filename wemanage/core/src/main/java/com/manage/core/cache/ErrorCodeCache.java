package com.manage.core.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.core.dao.ErrorCodeDao;
import com.manage.core.po.Error;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ErrorCodeCache extends BaseSysCacheAbstract {

	@Autowired
	private ErrorCodeDao errorCodeDao;

	private static Map<String, Error> map = new ConcurrentHashMap<>();

	@PostConstruct
	@Override
	public void init() {
		// if (map.isEmpty()) {
		log.info("ErrorCodeCache开始缓存====>");
		List<Error> errorList = errorCodeDao.selectErrorCodeList();
		for (Error error : errorList) {
			map.put(error.getErrorKey(), error);
		}
		log.info("ErrorCodeCache缓存结束====>" + map.size());
		// }
	}

	public static Error get(String key) {
		return map.get(key);
	}
}
