package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemRecord;
import com.mtx.system.dao.model.SystemRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemRecordMapper {
    long countByExample(SystemRecordExample example);

    int deleteByExample(SystemRecordExample example);

    int deleteByPrimaryKey(Integer recordId);

    int insert(SystemRecord record);

    int insertSelective(SystemRecord record);

    List<SystemRecord> selectByExample(SystemRecordExample example);

    SystemRecord selectByPrimaryKey(Integer recordId);

    int updateByExampleSelective(@Param("record") SystemRecord record, @Param("example") SystemRecordExample example);

    int updateByExample(@Param("record") SystemRecord record, @Param("example") SystemRecordExample example);

    int updateByPrimaryKeySelective(SystemRecord record);

    int updateByPrimaryKey(SystemRecord record);
}