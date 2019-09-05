package com.mtx.system.rpc.mapper;


import com.mtx.system.dao.vo.SystemOrganizationVo;

public interface SystemOrganizationExtMapper {


    SystemOrganizationVo selectByIdWithLeft(int organizationId);
}
