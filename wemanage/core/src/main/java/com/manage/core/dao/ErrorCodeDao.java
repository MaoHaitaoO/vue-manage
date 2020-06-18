package com.manage.core.dao;

import java.util.List;

import com.manage.core.po.Error;

public interface ErrorCodeDao {
    Error selectByKey(String errorKey);

    List<Error> selectErrorCodeList();
}