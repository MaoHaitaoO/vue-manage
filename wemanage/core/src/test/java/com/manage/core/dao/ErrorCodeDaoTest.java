package com.manage.core.dao;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.manage.core.CoreApplication;
import com.manage.core.cache.ErrorCodeCache;
import com.manage.core.po.Error;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CoreApplication.class})
public class ErrorCodeDaoTest {

	@Autowired
	private ErrorCodeDao errorCodeDao;
	@Autowired
	private ErrorCodeCache cache;

	@org.junit.Test
	public void selectByCode() {
//		String msg = ErrorCodeCache.get("USER_NOT_EXIST").getErrorMsg();
//		Error error = errorCodeDao.selectByKey("USER_NOT_EXIST");
//		System.out.println(msg);
	}

	@org.junit.Test
	public void selectErrorCodeList() {
		List<Error> errors = errorCodeDao.selectErrorCodeList();
		System.out.println(errors.size());
	}
}
