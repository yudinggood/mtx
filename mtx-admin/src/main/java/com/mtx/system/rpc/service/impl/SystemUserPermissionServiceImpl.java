package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.system.dao.mapper.SystemUserPermissionMapper;
import com.mtx.system.dao.model.SystemUserPermission;
import com.mtx.system.dao.model.SystemUserPermissionExample;
import com.mtx.system.rpc.api.SystemUserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SystemUserPermissionService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemUserPermissionServiceImpl extends BaseServiceImpl<SystemUserPermissionMapper, SystemUserPermission, SystemUserPermissionExample> implements SystemUserPermissionService {

    @Autowired
    SystemUserPermissionMapper systemUserPermissionMapper;

}