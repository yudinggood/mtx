package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemUserPermission;
import com.mtx.system.dao.model.SystemUserPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemUserPermissionMapper {
    long countByExample(SystemUserPermissionExample example);

    int deleteByExample(SystemUserPermissionExample example);

    int deleteByPrimaryKey(Integer userPermissionId);

    int insert(SystemUserPermission record);

    int insertSelective(SystemUserPermission record);

    List<SystemUserPermission> selectByExample(SystemUserPermissionExample example);

    SystemUserPermission selectByPrimaryKey(Integer userPermissionId);

    int updateByExampleSelective(@Param("record") SystemUserPermission record, @Param("example") SystemUserPermissionExample example);

    int updateByExample(@Param("record") SystemUserPermission record, @Param("example") SystemUserPermissionExample example);

    int updateByPrimaryKeySelective(SystemUserPermission record);

    int updateByPrimaryKey(SystemUserPermission record);
}