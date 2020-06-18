package com.manage.server.service;

import javax.servlet.http.HttpServletRequest;

import com.manage.server.vo.permission.QueryPermissionListRes;

public interface PermissionService {

	QueryPermissionListRes queryPermissionList(int roleId);

	QueryPermissionListRes queryViewPermissionList(HttpServletRequest request);

}
