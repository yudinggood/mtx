package com.mtx.system.rpc.mapper;


import com.mtx.system.dao.dto.SystemTaskDto;
import com.mtx.system.dao.vo.SystemTaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemTaskExtMapper {


    List<SystemTaskVo> list(@Param("systemTaskDto") SystemTaskDto systemTaskDto,
                              @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);


    
}
