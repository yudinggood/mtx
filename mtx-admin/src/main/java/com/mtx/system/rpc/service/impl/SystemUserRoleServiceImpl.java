package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.system.dao.mapper.SystemUserRoleMapper;
import com.mtx.system.dao.model.SystemUserRole;
import com.mtx.system.dao.model.SystemUserRoleExample;
import com.mtx.system.rpc.api.SystemUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SystemUserRoleService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemUserRoleServiceImpl extends BaseServiceImpl<SystemUserRoleMapper, SystemUserRole, SystemUserRoleExample> implements SystemUserRoleService {

    @Autowired
    SystemUserRoleMapper systemUserRoleMapper;

}