package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.mapper.SystemSystemMapper;
import com.mtx.system.dao.model.SystemSystem;
import com.mtx.system.dao.model.SystemSystemExample;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.dao.model.SystemUserExample;
import com.mtx.system.rpc.api.SystemSystemService;
import com.mtx.system.rpc.factory.SystemSystemFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SystemSystemService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemSystemServiceImpl extends BaseServiceImpl<SystemSystemMapper, SystemSystem, SystemSystemExample> implements SystemSystemService {
    private SystemSystemFactory systemSystemFactory=new SystemSystemFactory();
    @Autowired
    SystemSystemMapper systemSystemMapper;

    @Override
    public List<SelectVo> selectForSelect() {
        List<SystemSystem> systemSystemList =systemSystemMapper.selectByExampleWithBLOBs(new SystemSystemExample());
        List<SelectVo> selectVoList = systemSystemFactory .convertList(systemSystemList,SelectVo.class);

        return selectVoList;
    }

    @Override
    public SystemSystem selectByName(String name) {
        SystemSystemExample systemSystemExample=new SystemSystemExample();
        SystemSystemExample.Criteria criteria = systemSystemExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<SystemSystem> list =systemSystemMapper.selectByExample(systemSystemExample);
        try {
            return list.get(0);
        }catch (Exception e){
            return null;
        }

    }
}