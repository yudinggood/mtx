package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemSystem;
import com.mtx.system.dao.model.SystemSystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemSystemMapper {
    long countByExample(SystemSystemExample example);

    int deleteByExample(SystemSystemExample example);

    int deleteByPrimaryKey(Integer systemId);

    int insert(SystemSystem record);

    int insertSelective(SystemSystem record);

    List<SystemSystem> selectByExampleWithBLOBs(SystemSystemExample example);

    List<SystemSystem> selectByExample(SystemSystemExample example);

    SystemSystem selectByPrimaryKey(Integer systemId);

    int updateByExampleSelective(@Param("record") SystemSystem record, @Param("example") SystemSystemExample example);

    int updateByExampleWithBLOBs(@Param("record") SystemSystem record, @Param("example") SystemSystemExample example);

    int updateByExample(@Param("record") SystemSystem record, @Param("example") SystemSystemExample example);

    int updateByPrimaryKeySelective(SystemSystem record);

    int updateByPrimaryKeyWithBLOBs(SystemSystem record);

    int updateByPrimaryKey(SystemSystem record);
}