package com.mtx.system.rpc.mapper;


import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.system.dao.model.SystemPermission;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.vo.SystemUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserExtMapper {


    List<SystemUserVo> list(@Param("organizationList")List<Integer> organizationList, @Param("search")String search,
                            @Param("page")Page<SystemUserVo> page, @Param("orderByField")String orderByField, @Param("isAsc")boolean isAsc);

    int deleteByPrimaryKeysFaker(List<Object> list);

    List<SystemRole> selectSystemRoleBySystemUserId(@Param("systemUserId")Integer systemUserId);

    List<SystemPermission> selectSystemPermissionBySystemUserId(@Param("systemUserId")Integer systemUserId);
}
