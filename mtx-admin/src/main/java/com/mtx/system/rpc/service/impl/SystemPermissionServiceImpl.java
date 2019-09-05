package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.JsonUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.tag.Menu;
import com.mtx.system.dao.dto.SystemPermissionDto;
import com.mtx.system.dao.mapper.SystemPermissionMapper;
import com.mtx.system.dao.model.*;
import com.mtx.system.dao.vo.SystemPermissionVo;
import com.mtx.system.dao.vo.Ztree;
import com.mtx.system.rpc.api.SystemPermissionService;
import com.mtx.system.rpc.api.SystemRoleService;
import com.mtx.system.rpc.factory.SystemPermissionFactory;
import com.mtx.system.rpc.mapper.SystemPermissionExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* SystemPermissionService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemPermissionServiceImpl extends BaseServiceImpl<SystemPermissionMapper, SystemPermission, SystemPermissionExample> implements SystemPermissionService {

    private SystemPermissionFactory systemPermissionFactory=new SystemPermissionFactory();
    @Autowired
    SystemPermissionMapper systemPermissionMapper;
    @Autowired
    SystemPermissionExtMapper systemPermissionExtMapper;
    @Autowired
    SystemRoleService systemRoleService;

    @Override
    public Map selectForMenu() {
        Map result =new HashMap();
        int i=0;
        while (i<2){
            SystemPermissionExample systemPermissionExample=new SystemPermissionExample();
            SystemPermissionExample.Criteria criteria = systemPermissionExample.createCriteria();
            criteria.andTypeEqualTo(TypeConversionUtil.objectToByte(2)).andMenuLevelEqualTo(TypeConversionUtil.objectToByte(i));
            List<SystemPermission> list =systemPermissionMapper.selectByExample(systemPermissionExample);
            List<Menu> menuList = systemPermissionFactory.convertList(list,Menu.class);
            result.put(i,menuList);
            i++;
        }
        return result;
    }

    @Override
    public List selectForTopMenu() {
        SystemPermissionExample systemPermissionExample=new SystemPermissionExample();
        SystemPermissionExample.Criteria criteria = systemPermissionExample.createCriteria();
        criteria.andPermissionPidEqualTo(6);
        List<SystemPermission> list =systemPermissionMapper.selectByExample(systemPermissionExample);
        List<SystemPermissionVo> voList= systemPermissionFactory.convertListDozer(list,SystemPermissionVo.class);
        return voList;
    }

    @Override
    public String selectForTree() {
        SystemPermissionExample systemPermissionExample=new SystemPermissionExample();
        systemPermissionExample.setOrderByClause("permission_order asc");
        List<SystemPermission> list=systemPermissionMapper.selectByExample(systemPermissionExample);
        List<Ztree> voList= systemPermissionFactory.convertListDozer(list,Ztree.class);
        String json = null;
        try {
            json = JsonUtil.toJson(voList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public SystemPermissionVo selectByIdWithLeft(int permissionId) {
        SystemPermissionVo systemPermissionVo=systemPermissionExtMapper.selectByIdWithLeft(permissionId);
        systemPermissionVo=systemPermissionFactory.convertDic(systemPermissionVo);
        return systemPermissionVo;
    }

    /*@Value("${common.enableSqlLogInterceptor}")
    private String enableSqlLogInterceptor ;
    @Override
    public String test() {
        return enableSqlLogInterceptor;
    }*/
    @Override
    public String test() {
        return null;
    }

    @Override
    public int insertDto(SystemPermissionDto systemPermissionDto) {
        SystemPermission systemPermission = systemPermissionFactory.convertDtoToDo(systemPermissionDto,SystemPermission.class);

        return systemPermissionMapper.insertSelective(systemPermission);
    }

    @Override
    public List<Menu> selectForMenuUseJs(int nowMenuId,List<SystemRole> roleList) {
        List<Integer> intList = systemPermissionFactory.convertIntList(roleList,"roleId");
        List<SystemPermission> list = systemPermissionExtMapper.selectByroleList(intList);
        List<Menu> menuList = systemPermissionFactory.convertList(list,Menu.class);
        menuList = systemPermissionFactory.convertRecursion(menuList,nowMenuId);
        return menuList;
    }

    @Override
    public int updateDto(SystemPermissionDto systemPermissionDto) {
        SystemPermission systemPermission = systemPermissionFactory.convertDtoToDo(systemPermissionDto,SystemPermission.class);
        return systemPermissionMapper.updateByPrimaryKey(systemPermission);
    }

    @Override
    public String selectForChooseTree(int id) {
        SystemPermissionExample systemPermissionExample=new SystemPermissionExample();
        systemPermissionExample.setOrderByClause("permission_order asc");
        List<SystemPermission> list=systemPermissionMapper.selectByExample(systemPermissionExample);
        List<SystemRolePermission> systemRolePermissionList = systemRoleService.selectPermissionByRole(id);
        List<Ztree> voList= systemPermissionFactory.convertChooseTree(list,systemRolePermissionList);
        String json = null;
        try {
            json = JsonUtil.toJson(voList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}








