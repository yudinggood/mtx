package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemOrgDto;
import com.mtx.system.dao.mapper.SystemOrganizationMapper;
import com.mtx.system.dao.model.SystemOrganization;
import com.mtx.system.dao.model.SystemOrganizationExample;
import com.mtx.system.dao.vo.SystemOrganizationVo;

import java.util.List;

/**
* 降级实现SystemOrganizationService接口
* Created by yu on 2018/12/9.
*/
public class SystemOrganizationServiceMock extends BaseServiceMock<SystemOrganizationMapper, SystemOrganization, SystemOrganizationExample> implements SystemOrganizationService {
    @Override
    public String selectForTree() {
        return null;
    }

    @Override
    public SystemOrganizationVo selectByIdWithLeft(int organizationId) {
        return null;
    }

    @Override
    public int insertDto(SystemOrgDto systemOrgDto) {
        return 0;
    }

    @Override
    public int updateDto(SystemOrgDto systemOrgDto) {
        return 0;
    }

    @Override
    public List<SelectVo> selectForSelect() {
        return null;
    }

    @Override
    public List<SelectVo> selectForSelect(int userId) {
        return null;
    }

    @Override
    public void selectSubResult(int id) {

    }
}
