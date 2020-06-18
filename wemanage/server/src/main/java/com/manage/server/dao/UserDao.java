package com.manage.server.dao;

import java.util.List;

import com.manage.core.po.User;

public interface UserDao {

	int deleteById(Integer userId);

	int insert(User record);

	int batchInsert(List<User> list);

	User selectById(Integer userId);

	List<User> selectByIds(List<Integer> list);

	List<User> findAll();

	int updateById(User record);

	User selectByUsername(String username);
}
