package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.system.dao.mapper.SystemUserOrganizationMapper;
import com.mtx.system.dao.model.SystemUserOrganization;
import com.mtx.system.dao.model.SystemUserOrganizationExample;
import com.mtx.system.rpc.api.SystemUserOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SystemUserOrganizationService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemUserOrganizationServiceImpl extends BaseServiceImpl<SystemUserOrganizationMapper, SystemUserOrganization, SystemUserOrganizationExample> implements SystemUserOrganizationService {

    @Autowired
    SystemUserOrganizationMapper systemUserOrganizationMapper;

}