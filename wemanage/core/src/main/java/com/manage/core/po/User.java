package com.manage.core.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class User implements Serializable {

	private static final long serialVersionUID = 10001L;

	/**
	 * 主键
	 */
	private Integer userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String userPwd;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户状态 1、可用 2、禁用
	 */
	private String status;

	private String head;

	private Date createTime;

	private Date updateTime;
}
