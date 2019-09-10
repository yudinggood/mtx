package com.mtx.system.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseService;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemRoleDto;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.model.SystemRoleExample;
import com.mtx.system.dao.model.SystemRolePermission;
import com.mtx.system.dao.vo.SystemRoleVo;

import java.util.List;

/**
* SystemRoleService接口
* Created by yu on 2018/12/9.
*/
public interface SystemRoleService extends BaseService<SystemRole, SystemRoleExample> {



    SystemRoleVo selectByIdWithLeft(int roleId);

    int insertDto(SystemRoleDto systemRoleDto);

    int updateDto(SystemRoleDto systemRoleDto);

    List<SystemRolePermission> selectPermissionByRole(Integer roleId);

    int savePermission(JSONArray datas, int id);

    List<SystemRole> list(Page<SystemRole> page, String search);

    List<SelectVo> selectForSelect();

    List<SelectVo> selectForSelect(int userId);

    Integer selectByCode(String commonRole);
}