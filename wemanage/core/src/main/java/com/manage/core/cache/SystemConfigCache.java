package com.manage.core.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.core.dao.SystemConfigDao;
import com.manage.core.po.SystemConfig;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SystemConfigCache extends BaseSysCacheAbstract {

	@Autowired
	private SystemConfigDao systemConfigDao;

	private static Map<String, String> map = new ConcurrentHashMap<>();

	@PostConstruct
	@Override
	public void init() {
		// if (map.isEmpty()) {
		log.info("SystemConfigCache开始缓存====>");
		List<SystemConfig> systemConfigList = systemConfigDao.selectList();
		for (SystemConfig systemConfig : systemConfigList) {
			map.put(systemConfig.getConfigKey(), systemConfig.getConfigValue());
		}
		log.info("SystemConfigCache缓存结束====>" + map.size());
		// }
	}

	public static String get(String key) {
		return map.get(key);
	}

	public static int getInt(String key) {
		return Integer.parseInt(get(key));
	}
}
