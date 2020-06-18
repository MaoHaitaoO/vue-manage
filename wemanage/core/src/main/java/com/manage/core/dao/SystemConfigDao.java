package com.manage.core.dao;

import java.util.List;

import com.manage.core.po.SystemConfig;

public interface SystemConfigDao {

    int insert(SystemConfig record);

    SystemConfig selectByKey(String configKey);

    List<SystemConfig> selectList();
}