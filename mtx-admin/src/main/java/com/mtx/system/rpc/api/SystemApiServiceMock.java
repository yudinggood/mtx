package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.mapper.SystemAttachMapper;
import com.mtx.system.dao.mapper.SystemPermissionMapper;
import com.mtx.system.dao.model.*;

import java.util.List;

public class SystemApiServiceMock  implements SystemApiService{

    @Override
    public List<SystemPermission> selectSystemPermissionBySystemUserId(Integer systemUserId) {
        return null;
    }

    @Override
    public List<SystemPermission> selectSystemPermissionBySystemUserIdByCache(Integer systemUserId) {
        return null;
    }

    @Override
    public List<SystemRole> selectSystemRoleBySystemUserId(Integer systemUserId) {
        return null;
    }

    @Override
    public List<SystemRole> selectSystemRoleBySystemUserIdByCache(Integer systemUserId) {
        return null;
    }

    @Override
    public List<SystemRolePermission> selectSystemRolePermisstionBySystemRoleId(Integer systemRoleId) {
        return null;
    }

    @Override
    public List<SystemUserPermission> selectSystemUserPermissionBySystemUserId(Integer systemUserId) {
        return null;
    }

    @Override
    public List<SystemSystem> selectSystemSystemByExample(SystemSystemExample systemSystemExample) {
        return null;
    }

    @Override
    public List<SystemOrganization> selectSystemOrganizationByExample(SystemOrganizationExample systemOrganizationExample) {
        return null;
    }

    @Override
    public SystemUser selectSystemUserByUsername(String username) {
        return null;
    }

    @Override
    public int insertSystemLogSelective(SystemLog record) {
        return 0;
    }
}
