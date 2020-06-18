package com.manage.core.dao;

import com.manage.core.po.Permission;

public interface PermissionDao {
	Permission selectByPrimaryKey(Integer permissionId);
}
