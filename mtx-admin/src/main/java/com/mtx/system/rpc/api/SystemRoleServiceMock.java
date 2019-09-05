package com.mtx.system.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseServiceMock;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemRoleDto;
import com.mtx.system.dao.mapper.SystemRoleMapper;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.model.SystemRoleExample;
import com.mtx.system.dao.model.SystemRolePermission;
import com.mtx.system.dao.vo.SystemRoleVo;

import java.util.List;

/**
* 降级实现SystemRoleService接口
* Created by yu on 2018/12/9.
*/
public class SystemRoleServiceMock extends BaseServiceMock<SystemRoleMapper, SystemRole, SystemRoleExample> implements SystemRoleService {
    @Override
    public List<SystemRole> list(Page<SystemRole> page, String search) {
        return null;
    }

    @Override
    public SystemRoleVo selectByIdWithLeft(int roleId) {
        return null;
    }

    @Override
    public int insertDto(SystemRoleDto systemRoleDto) {
        return 0;
    }

    @Override
    public int updateDto(SystemRoleDto systemRoleDto) {
        return 0;
    }

    @Override
    public List<SystemRolePermission> selectPermissionByRole(Integer roleId) {
        return null;
    }

    @Override
    public int savePermission(JSONArray datas, int id) {
        return 0;
    }

    @Override
    public List<SelectVo> selectForSelect() {
        return null;
    }

    @Override
    public List<SelectVo> selectForSelect(int userId) {
        return null;
    }
}
