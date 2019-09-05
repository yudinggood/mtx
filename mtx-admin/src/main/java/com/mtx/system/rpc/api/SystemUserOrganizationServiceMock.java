package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.mapper.SystemUserOrganizationMapper;
import com.mtx.system.dao.model.SystemUserOrganization;
import com.mtx.system.dao.model.SystemUserOrganizationExample;

/**
* 降级实现SystemUserOrganizationService接口
* Created by yu on 2018/12/9.
*/
public class SystemUserOrganizationServiceMock extends BaseServiceMock<SystemUserOrganizationMapper, SystemUserOrganization, SystemUserOrganizationExample> implements SystemUserOrganizationService {

}
