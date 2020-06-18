package com.manage.server.vo.permission;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class QueryPermissionListReq {

	@NotNull(message = "角色编号不能为空")
	private int roleId;
}
