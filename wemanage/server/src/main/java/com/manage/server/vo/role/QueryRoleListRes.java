package com.manage.server.vo.role;

import java.util.List;

import com.manage.core.po.Role;
import com.manage.core.vo.BaseDataRes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryRoleListRes extends BaseDataRes {

	private List<Role> list;
}
