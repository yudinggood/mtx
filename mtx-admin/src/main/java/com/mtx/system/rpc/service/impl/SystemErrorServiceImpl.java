package com.mtx.system.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.RegularUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.system.dao.dto.SystemErrorDto;
import com.mtx.system.dao.mapper.SystemErrorMapper;
import com.mtx.system.dao.model.SystemError;
import com.mtx.system.dao.model.SystemErrorExample;
import com.mtx.system.dao.vo.SystemErrorVo;
import com.mtx.system.rpc.api.SystemErrorService;
import com.mtx.system.rpc.mapper.SystemErrorExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SystemErrorService实现
* Created by yu on 2018/12/27.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemErrorServiceImpl extends BaseServiceImpl<SystemErrorMapper, SystemError, SystemErrorExample> implements SystemErrorService {

    @Autowired
    SystemErrorMapper systemErrorMapper;
    @Autowired
    SystemErrorExtMapper systemErrorExtMapper;

    @Override
    public List<SystemErrorVo> list(Page<SystemError> page, SystemErrorDto systemErrorDto) {
        if(!ToolUtil.isEmpty(systemErrorDto.getEditDate())){
            List<String> temp= RegularUtil.getRegularByString("(.*) - (.*)",systemErrorDto.getEditDate());
            systemErrorDto.setStartDate(temp.get(1));
            systemErrorDto.setEndDate(temp.get(2));
        }
        List<SystemErrorVo> list = systemErrorExtMapper.list(systemErrorDto,page,page.getOrderByField(),page.isAsc());
        return list;
    }
}