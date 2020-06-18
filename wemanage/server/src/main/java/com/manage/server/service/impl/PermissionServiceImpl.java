package com.manage.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.manage.core.po.User;
import com.manage.core.util.RedisUtil;
import com.manage.server.dao.PermissionShowDao;
import com.manage.server.service.PermissionService;
import com.manage.server.vo.permission.PermissionTree;
import com.manage.server.vo.permission.QueryPermissionListRes;

@Service
public class PermissionServiceImpl implements PermissionService {

	private static final int ROOT = -1;

	@Autowired
	private PermissionShowDao permissionShowDao;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public QueryPermissionListRes queryPermissionList(int roleId) {
		List<PermissionTree> list = new ArrayList<>();
		list.addAll(recursion(roleId, ROOT));

		return buildQueryPermissionListRes(list);
	}

	@Override
	public QueryPermissionListRes queryViewPermissionList(HttpServletRequest request) {
		String token = request.getHeader("token");
		User user = (User) redisUtil.get(token);

		List<PermissionTree> list = new ArrayList<>();
		list.addAll(recursionView(user.getUserId(), ROOT));

		return buildQueryPermissionListRes(list);
	}

	private List<PermissionTree> recursion(int roleId, int parentId) {
		List<PermissionTree> parentList = permissionShowDao.selectPermissionByRoleIdAndParentId(roleId, parentId);

		if (!CollectionUtils.isEmpty(parentList)) {
			for (PermissionTree permissionTree : parentList) {
				permissionTree.setChildren(recursion(roleId, permissionTree.getPermissionId()));
			}
		}
		return parentList;
	}

	private List<PermissionTree> recursionView(int userId, int parentId) {
		List<PermissionTree> parentList = permissionShowDao.selectViewPermissionByUserIdAndParentId(userId, parentId);

		if (!CollectionUtils.isEmpty(parentList)) {
			for (PermissionTree permissionTree : parentList) {
				permissionTree.setChildren(recursionView(userId, permissionTree.getPermissionId()));
			}
		}
		return parentList;
	}

	private QueryPermissionListRes buildQueryPermissionListRes(List<PermissionTree> list) {
		QueryPermissionListRes res = new QueryPermissionListRes();
		res.setList(list);
		return res;
	}
}
