package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemError;
import com.mtx.system.dao.model.SystemErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemErrorMapper {
    long countByExample(SystemErrorExample example);

    int deleteByExample(SystemErrorExample example);

    int deleteByPrimaryKey(Integer errorId);

    int insert(SystemError record);

    int insertSelective(SystemError record);

    List<SystemError> selectByExampleWithBLOBs(SystemErrorExample example);

    List<SystemError> selectByExample(SystemErrorExample example);

    SystemError selectByPrimaryKey(Integer errorId);

    int updateByExampleSelective(@Param("record") SystemError record, @Param("example") SystemErrorExample example);

    int updateByExampleWithBLOBs(@Param("record") SystemError record, @Param("example") SystemErrorExample example);

    int updateByExample(@Param("record") SystemError record, @Param("example") SystemErrorExample example);

    int updateByPrimaryKeySelective(SystemError record);

    int updateByPrimaryKeyWithBLOBs(SystemError record);

    int updateByPrimaryKey(SystemError record);
}