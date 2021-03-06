package com.mtx.system.common.shiro;

import com.mtx.system.common.shiro.session.UpmsSessionDao;
import com.mtx.system.dao.model.SystemPermission;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.rpc.api.SystemApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户认证和授权
 */

@Slf4j
public class UpmsRealm extends AuthorizingRealm {


    @Autowired
    private SystemApiService systemApiService;
    @Autowired
    UpmsSessionDao upmsSessionDao;
    /**
     * 授权：验证权限时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //改成只走第一次，之后都调用缓存
        String username = (String) principalCollection.getPrimaryPrincipal();
        SystemUser systemUser = systemApiService.selectSystemUserByUsername(username);

        // 当前用户所有角色
        List<SystemRole> systemRoles = systemApiService.selectSystemRoleBySystemUserId(systemUser.getUserId());
        Set<String> roles = new HashSet<>();
        for (SystemRole systemRole : systemRoles) {
            if (StringUtils.isNotBlank(systemRole.getRoleName())) {
                roles.add(systemRole.getRoleName());
            }
        }

        // 当前用户所有权限
        List<SystemPermission> systemPermissions = systemApiService.selectSystemPermissionBySystemUserId(systemUser.getUserId());
        Set<String> permissions = new HashSet<>();
        for (SystemPermission systemPermission : systemPermissions) {
            if (StringUtils.isNotBlank(systemPermission.getPermissionValue())) {
                permissions.add(systemPermission.getPermissionValue());
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证：登录时调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();

        SystemUser systemUser = systemApiService.selectSystemUserByUsername(username);


        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                systemUser.getPassword(), //密码
                ByteSource.Util.bytes(systemUser.getSalt()),//salt
                getName()  //realm name
        );

        if (null == systemUser) {
            throw new UnknownAccountException();
        }
        if (systemUser.getUserState() == 2) {
            throw new LockedAccountException();
        }


        return authenticationInfo;
    }

}
