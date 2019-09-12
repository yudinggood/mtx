package com.mtx.system.rpc.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.vo.SystemRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemRoleExtMapper {


    List<SystemRoleVo> list(@Param("search")String search, @Param("page")Page<SystemRole> page,
                            @Param("orderByField")String orderByField, @Param("isAsc")boolean isAsc);
}
