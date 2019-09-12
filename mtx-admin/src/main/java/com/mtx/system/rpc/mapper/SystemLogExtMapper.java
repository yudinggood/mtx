package com.mtx.system.rpc.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.system.dao.model.SystemLog;
import com.mtx.system.dao.vo.SystemLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemLogExtMapper {


    List<SystemLogVo> list(@Param("search")String search, @Param("page")Page<SystemLog> page,
                           @Param("orderByField")String orderByField, @Param("isAsc")boolean isAsc);
}
