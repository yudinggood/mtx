package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.mapper.SystemUserRoleMapper;
import com.mtx.system.dao.model.SystemUserRole;
import com.mtx.system.dao.model.SystemUserRoleExample;

/**
* 降级实现SystemUserRoleService接口
* Created by yu on 2018/12/9.
*/
public class SystemUserRoleServiceMock extends BaseServiceMock<SystemUserRoleMapper, SystemUserRole, SystemUserRoleExample> implements SystemUserRoleService {

}
