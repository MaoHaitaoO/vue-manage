package com.manage.server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manage.core.cache.ErrorCodeCache;
import com.manage.core.cache.SystemConfigCache;
import com.manage.core.vo.R;

@RestController
@RequestMapping("/cache")
public class CacheController {

	@Autowired
	private ErrorCodeCache errorCodeCache;
	@Autowired
	private SystemConfigCache systemConfigCache;

	/**
	 * 待优化
	 * 
	 * @return
	 */
	@RequiresPermissions("cache:refresh")
	@GetMapping("/refresh")
	public R refresh() {
		errorCodeCache.init();
		systemConfigCache.init();
		return R.ok();
	}
}
