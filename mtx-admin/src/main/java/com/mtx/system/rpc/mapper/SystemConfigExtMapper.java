package com.mtx.system.rpc.mapper;


import com.mtx.system.dao.dto.SystemConfigDto;
import com.mtx.system.dao.model.SystemConfig;
import com.mtx.system.dao.vo.SystemConfigVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemConfigExtMapper {


    List<SystemConfigVo> list(@Param("systemConfigDto") SystemConfigDto systemConfigDto,
                            @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);


    List<SystemConfigVo> selectForDic(String code);
}
