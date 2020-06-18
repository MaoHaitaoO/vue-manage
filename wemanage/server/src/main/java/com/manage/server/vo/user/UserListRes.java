package com.manage.server.vo.user;

import java.util.List;

import com.manage.core.vo.BaseDataPageRes;

import lombok.Data;

@Data
public class UserListRes extends BaseDataPageRes {

	private List<UserDTO> list;
}
