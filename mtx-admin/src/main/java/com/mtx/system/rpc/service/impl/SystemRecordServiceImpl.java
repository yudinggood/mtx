package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.system.dao.mapper.SystemRecordMapper;
import com.mtx.system.dao.model.SystemRecord;
import com.mtx.system.dao.model.SystemRecordExample;
import com.mtx.system.dao.vo.SystemRecordVo;
import com.mtx.system.dao.vo.TimeLineVo;
import com.mtx.system.rpc.api.SystemRecordService;
import com.mtx.system.rpc.factory.SystemRecordFactory;
import com.mtx.system.rpc.mapper.SystemRecordExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SystemRecordService实现
* Created by yu on 2019/10/3.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemRecordServiceImpl extends BaseServiceImpl<SystemRecordMapper, SystemRecord, SystemRecordExample> implements SystemRecordService {

    @Autowired
    SystemRecordMapper systemRecordMapper;
    @Autowired
    SystemRecordExtMapper systemRecordExtMapper;

    @Override
    public TimeLineVo timeline() {
        List<SystemRecordVo> list = systemRecordExtMapper.timeline();
        TimeLineVo timeLineVo = SystemRecordFactory.getInstance().convertTimeLine(list);
        return timeLineVo;
    }



}