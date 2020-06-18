package com.manage.server.vo.user;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ExportUserReq {

	@NotNull(message = "id不能为空")
	private List<Integer> list;
}
