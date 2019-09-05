package com.mtx.system.rpc.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.system.dao.model.SystemLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemLogExtMapper {


    List<SystemLog> list(@Param("search")String search, @Param("page")Page<SystemLog> page,
                         @Param("orderByField")String orderByField, @Param("isAsc")boolean isAsc);
}
