package com.manage.server.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manage.core.po.User;
import com.manage.core.vo.R;
import com.manage.server.service.UserService;
import com.manage.server.vo.user.ExportUserReq;
import com.manage.server.vo.user.UserDTO;
import com.manage.server.vo.user.UserListReq;
import com.manage.server.vo.user.UserListRes;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public R getUser(@Valid @NotNull int id) {
		UserDTO user = userService.getUser(id);
		return R.ok().data(user);
	}

	@RequiresPermissions("user:list")
	@GetMapping("/list")
	public R userList(@Valid UserListReq req) {
		UserListRes res = userService.userList(req);
		return R.ok().data(res);
	}

	@PostMapping
	public R save(@Valid User user) {

		return R.ok();
	}

	@DeleteMapping
	public R delete(@Valid @NotNull int id) {

		return R.ok();
	}

	@PutMapping
	public R update(@Valid User user) {

		return R.ok();
	}

	@PostMapping("/export")
	public void exportUser(HttpServletResponse response, @Valid ExportUserReq req) {
		 userService.exportUser(response, req);
	}

	@PostMapping("/import")
	public R importUser(@RequestParam("file") MultipartFile multipartFile) {
		userService.importUser(multipartFile);
		return R.ok();
	}


}
