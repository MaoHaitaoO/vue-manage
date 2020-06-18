package com.manage.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manage.core.po.Role;

public interface UserRoleDao {

	List<Role> selectRoleByUserId(@Param("userId") int userId);

}