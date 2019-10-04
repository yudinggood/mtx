package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemTask;
import com.mtx.system.dao.model.SystemTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemTaskMapper {
    long countByExample(SystemTaskExample example);

    int deleteByExample(SystemTaskExample example);

    int deleteByPrimaryKey(Integer taskId);

    int insert(SystemTask record);

    int insertSelective(SystemTask record);

    List<SystemTask> selectByExample(SystemTaskExample example);

    SystemTask selectByPrimaryKey(Integer taskId);

    int updateByExampleSelective(@Param("record") SystemTask record, @Param("example") SystemTaskExample example);

    int updateByExample(@Param("record") SystemTask record, @Param("example") SystemTaskExample example);

    int updateByPrimaryKeySelective(SystemTask record);

    int updateByPrimaryKey(SystemTask record);
}