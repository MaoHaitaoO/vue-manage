package com.manage.core.dao;

import com.manage.core.po.User;

public interface UserLoginDao {
	User selectByUsername(String username);
}
