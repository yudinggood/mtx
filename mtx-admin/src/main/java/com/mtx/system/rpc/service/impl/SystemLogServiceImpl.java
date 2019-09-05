package com.mtx.system.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.system.dao.mapper.SystemLogMapper;
import com.mtx.system.dao.model.SystemLog;
import com.mtx.system.dao.model.SystemLogExample;
import com.mtx.system.rpc.api.SystemLogService;
import com.mtx.system.rpc.mapper.SystemLogExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SystemLogService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemLogServiceImpl extends BaseServiceImpl<SystemLogMapper, SystemLog, SystemLogExample> implements SystemLogService {

    @Autowired
    SystemLogMapper systemLogMapper;
    @Autowired
    SystemLogExtMapper systemLogExtMapper;

    @Override
    public List<SystemLog> list(Page<SystemLog> page, String search) {
        List<SystemLog> list = systemLogExtMapper.list(search,page,page.getOrderByField(),page.isAsc());
        return list;
    }


}