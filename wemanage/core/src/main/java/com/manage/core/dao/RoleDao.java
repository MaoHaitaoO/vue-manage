package com.manage.core.dao;

import java.util.List;

import com.manage.core.po.Role;

public interface RoleDao {
	Role selectByPrimaryKey(Integer roleId);

	List<Role> selectList();

}
