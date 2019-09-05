package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.dto.SystemErrorDto;
import com.mtx.system.dao.mapper.SystemErrorMapper;
import com.mtx.system.dao.model.SystemError;
import com.mtx.system.dao.model.SystemErrorExample;

import java.util.List;

/**
* 降级实现SystemErrorService接口
* Created by yu on 2018/12/27.
*/
public class SystemErrorServiceMock extends BaseServiceMock<SystemErrorMapper, SystemError, SystemErrorExample> implements SystemErrorService {
    @Override
    public List<SystemError> list(Page<SystemError> page,SystemErrorDto systemErrorDto) {
        return null;
    }
}
