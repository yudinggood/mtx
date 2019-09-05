package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseService;
import com.mtx.system.dao.model.*;

import java.util.List;

/**
 * 权限接口
 */
public interface SystemApiService   {
    /**
     * 根据用户id获取所拥有的权限(用户和角色权限合集)
     * @param systemUserId
     * @return
     */
    List<SystemPermission> selectSystemPermissionBySystemUserId(Integer systemUserId);

    /**
     * 根据用户id获取所拥有的权限(用户和角色权限合集)
     * @param systemUserId
     * @return
     */
    List<SystemPermission> selectSystemPermissionBySystemUserIdByCache(Integer systemUserId);

    /**
     * 根据用户id获取所属的角色
     * @param systemUserId
     * @return
     */
    List<SystemRole> selectSystemRoleBySystemUserId(Integer systemUserId);

    /**
     * 根据用户id获取所属的角色
     * @param systemUserId
     * @return
     */
    List<SystemRole> selectSystemRoleBySystemUserIdByCache(Integer systemUserId);

    /**
     * 根据角色id获取所拥有的权限
     * @param systemRoleId
     * @return
     */
    List<SystemRolePermission> selectSystemRolePermisstionBySystemRoleId(Integer systemRoleId);

    /**
     * 根据用户id获取所拥有的权限
     * @param systemUserId
     * @return
     */
    List<SystemUserPermission> selectSystemUserPermissionBySystemUserId(Integer systemUserId);

    /**
     * 根据条件获取系统数据
     * @param systemSystemExample
     * @return
     */
    List<SystemSystem> selectSystemSystemByExample(SystemSystemExample systemSystemExample);

    /**
     * 根据条件获取组织数据
     * @param systemOrganizationExample
     * @return
     */
    List<SystemOrganization> selectSystemOrganizationByExample(SystemOrganizationExample systemOrganizationExample);

    /**
     * 根据username获取SystemUser
     * @param username
     * @return
     */
    SystemUser selectSystemUserByUsername(String username);

    /**
     * 写入操作日志
     * @param record
     * @return
     */
    int insertSystemLogSelective(SystemLog record);
}
