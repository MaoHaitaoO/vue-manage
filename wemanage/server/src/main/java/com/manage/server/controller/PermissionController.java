package com.manage.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manage.core.vo.R;
import com.manage.server.service.PermissionService;
import com.manage.server.vo.permission.QueryPermissionListReq;
import com.manage.server.vo.permission.QueryPermissionListRes;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@GetMapping("/list")
	public R queryList(@Valid QueryPermissionListReq req) {
		QueryPermissionListRes res = permissionService.queryPermissionList(req.getRoleId());
		return R.ok().data(res);
	}

	@GetMapping("/view")
	public R queryViewPermission(HttpServletRequest request) {
		QueryPermissionListRes res = permissionService.queryViewPermissionList(request);
		return R.ok().data(res);
	}



}
