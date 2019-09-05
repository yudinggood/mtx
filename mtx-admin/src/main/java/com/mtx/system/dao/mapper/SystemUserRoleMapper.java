package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemUserRole;
import com.mtx.system.dao.model.SystemUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemUserRoleMapper {
    long countByExample(SystemUserRoleExample example);

    int deleteByExample(SystemUserRoleExample example);

    int deleteByPrimaryKey(Integer userRoleId);

    int insert(SystemUserRole record);

    int insertSelective(SystemUserRole record);

    List<SystemUserRole> selectByExample(SystemUserRoleExample example);

    SystemUserRole selectByPrimaryKey(Integer userRoleId);

    int updateByExampleSelective(@Param("record") SystemUserRole record, @Param("example") SystemUserRoleExample example);

    int updateByExample(@Param("record") SystemUserRole record, @Param("example") SystemUserRoleExample example);

    int updateByPrimaryKeySelective(SystemUserRole record);

    int updateByPrimaryKey(SystemUserRole record);
}