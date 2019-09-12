package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.util.base.RegularUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.system.dao.mapper.SystemUserMapper;
import com.mtx.system.dao.model.*;
import com.mtx.system.rpc.api.SystemApiService;
import com.mtx.system.rpc.mapper.SystemUserExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemApiServiceImpl implements SystemApiService{
    @Autowired
    SystemUserMapper systemUserMapper;
    @Autowired
    SystemUserExtMapper systemUserExtMapper;

    @Override
    public List<SystemPermission> selectSystemPermissionBySystemUserId(Integer systemUserId) {
        // 用户不存在或锁定状态
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(systemUserId);
        if (null == systemUser || 2 == systemUser.getUserState()) {
            log.info("selectSystemPermissionBySystemUserId : systemUserId={}", systemUserId);
            return Collections.EMPTY_LIST;
        }
        List<SystemPermission> systemPermissions = systemUserExtMapper.selectSystemPermissionBySystemUserId(systemUserId);
        return systemPermissions;
    }

    @Override
    public List<SystemPermission> selectSystemPermissionBySystemUserIdByCache(Integer systemUserId) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<SystemRole> selectSystemRoleBySystemUserId(Integer systemUserId) {
        // 用户不存在或锁定状态
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(systemUserId);
        if (null == systemUser || 2 == systemUser.getUserState()) {
            log.info("selectSystemPermissionBySystemUserIdByCache : systemUserId={}", systemUserId);
            return Collections.EMPTY_LIST;
        }
        List<SystemRole> systemRoles = systemUserExtMapper.selectSystemRoleBySystemUserId(systemUserId);
        return systemRoles;
    }

    @Override
    public List<SystemRole> selectSystemRoleBySystemUserIdByCache(Integer systemUserId) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<SystemRolePermission> selectSystemRolePermisstionBySystemRoleId(Integer systemRoleId) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<SystemUserPermission> selectSystemUserPermissionBySystemUserId(Integer systemUserId) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<SystemSystem> selectSystemSystemByExample(SystemSystemExample systemSystemExample) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<SystemOrganization> selectSystemOrganizationByExample(SystemOrganizationExample systemOrganizationExample) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public SystemUser selectSystemUserByUsername(String username) {
        SystemUserExample systemUserExample = new SystemUserExample();
        SystemUserExample.Criteria criteria = systemUserExample.createCriteria();
        if(RegularUtil.getRegularResult(RegularUtil.PHONE,username)){
            criteria.andPhoneEqualTo(username);
        }
        if(RegularUtil.getRegularResult(RegularUtil.EMAIL,username)){
            criteria.andEmailEqualTo(username);
        }
        criteria.andUserStateNotEqualTo((byte) 3);
        List<SystemUser> systemUsers = systemUserMapper.selectByExample(systemUserExample);
        if (ToolUtil.isNotEmpty(systemUsers)) {
            return systemUsers.get(0);
        }
        return null;

    }

    @Override
    public int insertSystemLogSelective(SystemLog record) {
        return 0;
    }

    @Override
    public SystemUser selectSystemUserByUsernameUnauth(String username) {
        SystemUserExample systemUserExample = new SystemUserExample();
        SystemUserExample.Criteria criteria = systemUserExample.createCriteria();
        if(RegularUtil.getRegularResult(RegularUtil.PHONE,username)){
            criteria.andPhoneEqualTo(username);
        }
        if(RegularUtil.getRegularResult(RegularUtil.EMAIL,username)){
            criteria.andEmailEqualTo(username);
        }
        List<SystemUser> systemUsers = systemUserMapper.selectByExample(systemUserExample);
        if (ToolUtil.isNotEmpty(systemUsers)) {
            return systemUsers.get(0);
        }
        return null;
    }
}
