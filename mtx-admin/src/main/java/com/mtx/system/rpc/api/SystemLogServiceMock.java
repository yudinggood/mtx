package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.mapper.SystemLogMapper;
import com.mtx.system.dao.model.SystemLog;
import com.mtx.system.dao.model.SystemLogExample;

import java.util.List;

/**
* 降级实现SystemLogService接口
* Created by yu on 2018/12/9.
*/
public class SystemLogServiceMock extends BaseServiceMock<SystemLogMapper, SystemLog, SystemLogExample> implements SystemLogService {
    @Override
    public List<SystemLog> list(Page<SystemLog> page, String search) {
        return null;
    }
}
