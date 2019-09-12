package com.mtx.system.rpc.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.system.dao.dto.SystemErrorDto;
import com.mtx.system.dao.model.SystemError;
import com.mtx.system.dao.vo.SystemErrorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemErrorExtMapper {


    List<SystemErrorVo> list(@Param("systemErrorDto") SystemErrorDto systemErrorDto, @Param("page") Page<SystemError> page,
                             @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
