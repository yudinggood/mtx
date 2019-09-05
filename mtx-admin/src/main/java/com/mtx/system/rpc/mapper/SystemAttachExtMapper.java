package com.mtx.system.rpc.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.model.SystemAttach;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemAttachExtMapper {


    List<SystemAttach> list(@Param("systemAttachDto") SystemAttachDto systemAttachDto, @Param("page")Page<SystemAttach> page,
                            @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);



}
