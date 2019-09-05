package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemRolePermission;
import com.mtx.system.dao.model.SystemRolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemRolePermissionMapper {
    long countByExample(SystemRolePermissionExample example);

    int deleteByExample(SystemRolePermissionExample example);

    int deleteByPrimaryKey(Integer rolePermissionId);

    int insert(SystemRolePermission record);

    int insertSelective(SystemRolePermission record);

    List<SystemRolePermission> selectByExample(SystemRolePermissionExample example);

    SystemRolePermission selectByPrimaryKey(Integer rolePermissionId);

    int updateByExampleSelective(@Param("record") SystemRolePermission record, @Param("example") SystemRolePermissionExample example);

    int updateByExample(@Param("record") SystemRolePermission record, @Param("example") SystemRolePermissionExample example);

    int updateByPrimaryKeySelective(SystemRolePermission record);

    int updateByPrimaryKey(SystemRolePermission record);
}