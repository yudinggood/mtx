package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemOrganization;
import com.mtx.system.dao.model.SystemOrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemOrganizationMapper {
    long countByExample(SystemOrganizationExample example);

    int deleteByExample(SystemOrganizationExample example);

    int deleteByPrimaryKey(Integer organizationId);

    int insert(SystemOrganization record);

    int insertSelective(SystemOrganization record);

    List<SystemOrganization> selectByExample(SystemOrganizationExample example);

    SystemOrganization selectByPrimaryKey(Integer organizationId);

    int updateByExampleSelective(@Param("record") SystemOrganization record, @Param("example") SystemOrganizationExample example);

    int updateByExample(@Param("record") SystemOrganization record, @Param("example") SystemOrganizationExample example);

    int updateByPrimaryKeySelective(SystemOrganization record);

    int updateByPrimaryKey(SystemOrganization record);
}