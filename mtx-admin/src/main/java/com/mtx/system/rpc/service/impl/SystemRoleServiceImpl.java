package com.mtx.system.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemRoleDto;
import com.mtx.system.dao.mapper.SystemRoleMapper;
import com.mtx.system.dao.mapper.SystemRolePermissionMapper;
import com.mtx.system.dao.mapper.SystemUserRoleMapper;
import com.mtx.system.dao.model.*;
import com.mtx.system.dao.vo.SystemRoleVo;
import com.mtx.system.rpc.api.SystemRoleService;
import com.mtx.system.rpc.factory.SystemRoleFactory;
import com.mtx.system.rpc.mapper.SystemRoleExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* SystemRoleService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRoleMapper, SystemRole, SystemRoleExample> implements SystemRoleService {
    private SystemRoleFactory systemRoleFactory =new SystemRoleFactory();
    @Autowired
    SystemRoleMapper systemRoleMapper;
    @Autowired
    SystemRoleExtMapper systemRoleExtMapper;
    @Autowired
    SystemRolePermissionMapper systemRolePermissionMapper;
    @Autowired
    SystemRoleService systemRoleService;
    @Autowired
    SystemUserRoleMapper systemUserRoleMapper;


    @Override
    public List<SystemRoleVo> list(Page<SystemRole> page, String search) {
        List<SystemRoleVo> list = systemRoleExtMapper.list(search,page,page.getOrderByField(),page.isAsc());
        return list;
    }

    @Override
    public SystemRoleVo selectByIdWithLeft(int roleId) {
        SystemRole systemRole=systemRoleMapper.selectByPrimaryKey(roleId);
        SystemRoleVo systemRoleVo = systemRoleFactory.convertModel(systemRole,SystemRoleVo.class);
        return systemRoleVo;
    }

    @Override
    public int insertDto(SystemRoleDto systemRoleDto) {
        //判断名称是否重复
        SystemRoleExample systemRoleExample=new SystemRoleExample();
        SystemRoleExample.Criteria criteria = systemRoleExample.createCriteria();
        criteria.andRoleNameEqualTo(systemRoleDto.getRoleName());
        List<SystemRole> list =systemRoleMapper.selectByExample(systemRoleExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.ROLE10020001);
        }

        SystemRole systemRole = systemRoleFactory.convertDtoToDo(systemRoleDto,SystemRole.class);
        return systemRoleMapper.insertSelective(systemRole);
    }

    @Override
    public int updateDto(SystemRoleDto systemRoleDto) {
        //判断名称是否重复
        SystemRoleExample systemRoleExample=new SystemRoleExample();
        SystemRoleExample.Criteria criteria = systemRoleExample.createCriteria();
        criteria.andRoleNameEqualTo(systemRoleDto.getRoleName());
        criteria.andRoleIdNotEqualTo(systemRoleDto.getRoleId());
        List<SystemRole> list =systemRoleMapper.selectByExample(systemRoleExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.ROLE10020001);
        }

        SystemRole systemRole = systemRoleFactory.convertDtoToDo(systemRoleDto,SystemRole.class);
        return systemRoleMapper.updateByPrimaryKey(systemRole);
    }

    @Override
    public List<SystemRolePermission> selectPermissionByRole(Integer roleId) {
        SystemRolePermissionExample systemRolePermissionExample = new SystemRolePermissionExample();
        systemRolePermissionExample.createCriteria()
                .andRoleIdEqualTo(roleId);
        List<SystemRolePermission> systemRolePermissionList = systemRolePermissionMapper.selectByExample(systemRolePermissionExample);
        return systemRolePermissionList;
    }

    @Override
    public int savePermission(JSONArray datas, int id) {
        if(datas.size()==0){
            return 1;
        }
        List<Integer> deleteIds = new ArrayList<>();
        // 新增权限
        for (Object json:datas){
            if (!((JSONObject) json).getBoolean("checked")) {
                deleteIds.add(((JSONObject) json).getIntValue("id"));
            } else {
                SystemRolePermission systemRolePermission = new SystemRolePermission();
                systemRolePermission.setRoleId(id);
                systemRolePermission.setPermissionId(((JSONObject) json).getIntValue("id"));
                systemRolePermissionMapper.insertSelective(systemRolePermission);
            }
        }

        // 删除权限
        if (deleteIds.size() > 0) {
            SystemRolePermissionExample systemRolePermissionExample = new SystemRolePermissionExample();
            systemRolePermissionExample.createCriteria()
                    .andPermissionIdIn(deleteIds)
                    .andRoleIdEqualTo(id);
            systemRolePermissionMapper.deleteByExample(systemRolePermissionExample);
        }
        return datas.size();
    }

    @Override
    public List<SelectVo> selectForSelect() {
        List<SystemRole> systemRoleList =systemRoleMapper.selectByExample(new SystemRoleExample());
        List<SelectVo> selectVoList = systemRoleFactory .convertList(systemRoleList,SelectVo.class);

        return selectVoList;
    }

    @Override
    public List<SelectVo> selectForSelect(int userId) {
        SystemUserRoleExample systemUserRoleExample=new SystemUserRoleExample();
        systemUserRoleExample.createCriteria().andUserIdEqualTo(userId);
        List<SystemUserRole> systemRoleList =systemUserRoleMapper.selectByExample(systemUserRoleExample);
        List<SelectVo> selectVoList = systemRoleFactory .convertList(systemRoleList,SelectVo.class);

        return selectVoList;
    }

    @Override
    public Integer selectByCode(String commonRole) {
        SystemRoleExample systemRoleExample=new SystemRoleExample();
        SystemRoleExample.Criteria criteria = systemRoleExample.createCriteria();
        criteria.andRoleCodeEqualTo(commonRole);
        List<SystemRole> list =systemRoleMapper.selectByExample(systemRoleExample);
        if (ToolUtil.isNotEmpty(list)) {
            return list.get(0).getRoleId();
        }
        return null;
    }
}