package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemUserOrganization;
import com.mtx.system.dao.model.SystemUserOrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemUserOrganizationMapper {
    long countByExample(SystemUserOrganizationExample example);

    int deleteByExample(SystemUserOrganizationExample example);

    int deleteByPrimaryKey(Integer userOrganizationId);

    int insert(SystemUserOrganization record);

    int insertSelective(SystemUserOrganization record);

    List<SystemUserOrganization> selectByExample(SystemUserOrganizationExample example);

    SystemUserOrganization selectByPrimaryKey(Integer userOrganizationId);

    int updateByExampleSelective(@Param("record") SystemUserOrganization record, @Param("example") SystemUserOrganizationExample example);

    int updateByExample(@Param("record") SystemUserOrganization record, @Param("example") SystemUserOrganizationExample example);

    int updateByPrimaryKeySelective(SystemUserOrganization record);

    int updateByPrimaryKey(SystemUserOrganization record);
}