package com.mtx.system.dao.mapper;

import com.mtx.system.dao.model.SystemDic;
import com.mtx.system.dao.model.SystemDicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemDicMapper {
    long countByExample(SystemDicExample example);

    int deleteByExample(SystemDicExample example);

    int deleteByPrimaryKey(Integer dicId);

    int insert(SystemDic record);

    int insertSelective(SystemDic record);

    List<SystemDic> selectByExample(SystemDicExample example);

    SystemDic selectByPrimaryKey(Integer dicId);

    int updateByExampleSelective(@Param("record") SystemDic record, @Param("example") SystemDicExample example);

    int updateByExample(@Param("record") SystemDic record, @Param("example") SystemDicExample example);

    int updateByPrimaryKeySelective(SystemDic record);

    int updateByPrimaryKey(SystemDic record);
}