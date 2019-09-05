package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.system.dao.mapper.SystemRolePermissionMapper;
import com.mtx.system.dao.model.SystemRolePermission;
import com.mtx.system.dao.model.SystemRolePermissionExample;
import com.mtx.system.rpc.api.SystemRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SystemRolePermissionService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemRolePermissionServiceImpl extends BaseServiceImpl<SystemRolePermissionMapper, SystemRolePermission, SystemRolePermissionExample> implements SystemRolePermissionService {

    @Autowired
    SystemRolePermissionMapper systemRolePermissionMapper;

}