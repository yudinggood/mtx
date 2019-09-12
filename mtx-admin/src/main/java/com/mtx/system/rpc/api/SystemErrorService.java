package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseService;
import com.mtx.system.dao.dto.SystemErrorDto;
import com.mtx.system.dao.model.SystemError;
import com.mtx.system.dao.model.SystemErrorExample;
import com.mtx.system.dao.vo.SystemErrorVo;

import java.util.List;

/**
* SystemErrorService接口
* Created by yu on 2018/12/27.
*/
public interface SystemErrorService extends BaseService<SystemError, SystemErrorExample> {

    List<SystemErrorVo> list(Page<SystemError> page, SystemErrorDto systemErrorDto);
}