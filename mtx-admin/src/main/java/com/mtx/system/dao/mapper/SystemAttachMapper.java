package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemAttach;
import com.mtx.system.dao.model.SystemAttachExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface SystemAttachMapper {
    long countByExample(SystemAttachExample example);

    int deleteByExample(SystemAttachExample example);

    int deleteByPrimaryKey(Integer attachId);

    int insert(SystemAttach record);

    int insertSelective(SystemAttach record);

    List<SystemAttach> selectByExample(SystemAttachExample example);

    SystemAttach selectByPrimaryKey(Integer attachId);

    int updateByExampleSelective(@Param("record") SystemAttach record, @Param("example") SystemAttachExample example);

    int updateByExample(@Param("record") SystemAttach record, @Param("example") SystemAttachExample example);

    int updateByPrimaryKeySelective(SystemAttach record);

    int updateByPrimaryKey(SystemAttach record);
}