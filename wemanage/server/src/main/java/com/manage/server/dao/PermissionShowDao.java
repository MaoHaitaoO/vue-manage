package com.manage.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manage.server.vo.permission.PermissionTree;

public interface PermissionShowDao {

	List<PermissionTree> selectPermissionByRoleIdAndParentId(@Param("roleId") int roleId,
			@Param("parentId") int parentId);

	List<PermissionTree> selectViewPermissionByUserIdAndParentId(@Param("userId") int userId,
			@Param("parentId") int parentId);

}
