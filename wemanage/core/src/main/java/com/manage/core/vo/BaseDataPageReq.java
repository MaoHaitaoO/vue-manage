package com.manage.core.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseDataPageReq {

	@NotNull(message = "pageNum不能为空")
	private int pageNum;

	@NotNull(message = "pageSize不能为空")
	private int pageSize;
}
