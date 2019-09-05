package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseService;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemOrgDto;
import com.mtx.system.dao.model.SystemOrganization;
import com.mtx.system.dao.model.SystemOrganizationExample;
import com.mtx.system.dao.vo.SystemOrganizationVo;

import java.util.List;

/**
* SystemOrganizationService接口
* Created by yu on 2018/12/9.
*/
public interface SystemOrganizationService extends BaseService<SystemOrganization, SystemOrganizationExample> {

    String selectForTree();

    SystemOrganizationVo selectByIdWithLeft(int organizationId);

    int insertDto(SystemOrgDto systemOrgDto);

    int updateDto(SystemOrgDto systemOrgDto);

    List<SelectVo> selectForSelect();

    List<SelectVo> selectForSelect(int userId);

    void selectSubResult(int id);
}