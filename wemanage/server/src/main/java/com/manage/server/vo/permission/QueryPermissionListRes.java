package com.manage.server.vo.permission;

import java.util.List;

import com.manage.core.vo.BaseDataRes;

import lombok.Data;

@Data
public class QueryPermissionListRes extends BaseDataRes {

	private List<PermissionTree> list;
}
