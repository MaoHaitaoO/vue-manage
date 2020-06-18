package com.manage.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manage.core.po.Permission;

public interface RolePermissionDao {

	List<Permission> selectPermissionByRoleId(@Param("roleId") int roleId);

	List<Permission> selectPermissionByUserIdAndType(@Param("userId") int userId, @Param("type") String type);

	List<Permission> selectPermissionByRoleIdAndParentId(@Param("roleId") int roleId, @Param("parentId") int parentId);

}
