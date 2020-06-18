package com.manage.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manage.core.dao.RoleDao;
import com.manage.core.po.Role;
import com.manage.core.vo.R;
import com.manage.server.vo.role.QueryRoleListRes;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleDao roleDao;

	@GetMapping("/list")
	public R queryList() {
		List<Role> roles = roleDao.selectList();
		return R.ok().data(new QueryRoleListRes(roles));
	}
}
