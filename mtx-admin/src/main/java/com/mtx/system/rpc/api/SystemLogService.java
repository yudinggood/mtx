package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseService;
import com.mtx.system.dao.model.SystemLog;
import com.mtx.system.dao.model.SystemLogExample;

import java.util.List;

/**
* SystemLogService接口
* Created by yu on 2018/12/9.
*/
public interface SystemLogService extends BaseService<SystemLog, SystemLogExample> {

    List<SystemLog> list(Page<SystemLog> page, String search);
}