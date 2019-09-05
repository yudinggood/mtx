package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.mapper.SystemUserPermissionMapper;
import com.mtx.system.dao.model.SystemUserPermission;
import com.mtx.system.dao.model.SystemUserPermissionExample;

/**
* 降级实现SystemUserPermissionService接口
* Created by yu on 2018/12/9.
*/
public class SystemUserPermissionServiceMock extends BaseServiceMock<SystemUserPermissionMapper, SystemUserPermission, SystemUserPermissionExample> implements SystemUserPermissionService {

}
