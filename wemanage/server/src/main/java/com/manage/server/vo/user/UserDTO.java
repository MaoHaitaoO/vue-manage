package com.manage.server.vo.user;

import java.util.Date;

import com.manage.core.vo.BaseDataRes;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO extends BaseDataRes {

	private Integer userId;

	@Excel(name = "用户名")
	private String userName;

	@Excel(name = "昵称", orderNum = "1")
	private String nickName;

	@Excel(name = "手机号", orderNum = "2")
	private String mobile;

	@Excel(name = "邮箱", orderNum = "3")
	private String email;

	@Excel(name = "用户状态", replace = {"可用_1", "禁用_2"}, orderNum = "4")
	private String status;

	private String head;

	@Excel(name = "创建日期", exportFormat = "yyyy-MM-dd", orderNum = "5", importFormat = "yyyy-MM-dd")
	private Date createTime;

	@Excel(name = "最后一次修改日期", exportFormat = "yyyy-MM-dd", orderNum = "6", importFormat = "yyyy-MM-dd")
	private Date updateTime;
}
