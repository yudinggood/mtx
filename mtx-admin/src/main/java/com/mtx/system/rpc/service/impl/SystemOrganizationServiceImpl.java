package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.JsonUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemOrgDto;
import com.mtx.system.dao.mapper.SystemOrganizationMapper;
import com.mtx.system.dao.mapper.SystemUserOrganizationMapper;
import com.mtx.system.dao.model.SystemOrganization;
import com.mtx.system.dao.model.SystemOrganizationExample;
import com.mtx.system.dao.model.SystemUserOrganization;
import com.mtx.system.dao.model.SystemUserOrganizationExample;
import com.mtx.system.dao.vo.SystemOrganizationVo;
import com.mtx.system.dao.vo.Ztree;
import com.mtx.system.rpc.api.SystemOrganizationService;
import com.mtx.system.rpc.factory.SystemOrgFactory;
import com.mtx.system.rpc.mapper.SystemOrganizationExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
* SystemOrganizationService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemOrganizationServiceImpl extends BaseServiceImpl<SystemOrganizationMapper, SystemOrganization, SystemOrganizationExample> implements SystemOrganizationService {
    private SystemOrgFactory systemOrgFactory=new SystemOrgFactory();
    @Autowired
    SystemOrganizationMapper systemOrganizationMapper;
    @Autowired
    SystemOrganizationExtMapper systemOrganizationExtMapper;
    @Autowired
    SystemUserOrganizationMapper systemUserOrganizationMapper;

    @Override
    public String selectForTree() {
        SystemOrganizationExample systemOrganizationExample=new SystemOrganizationExample();
        systemOrganizationExample.setOrderByClause("organization_name asc");
        List<SystemOrganization> list=systemOrganizationMapper.selectByExample(systemOrganizationExample);
        List<Ztree> voList= systemOrgFactory.convertList(list,Ztree.class);
        String json = null;
        try {
            json = JsonUtil.toJson(voList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public SystemOrganizationVo selectByIdWithLeft(int organizationId) {
        SystemOrganizationVo systemOrganizationVo=systemOrganizationExtMapper.selectByIdWithLeft(organizationId);
        return systemOrganizationVo;
    }

    @Override
    public int insertDto(SystemOrgDto systemOrgDto) {
        //判断名称是否重复
        SystemOrganizationExample systemOrganizationExample=new SystemOrganizationExample();
        SystemOrganizationExample.Criteria criteria = systemOrganizationExample.createCriteria();
        criteria.andOrganizationNameEqualTo(systemOrgDto.getOrganizationName());
        criteria.andOrganizationPidEqualTo(1);
        List<SystemOrganization> list =systemOrganizationMapper.selectByExample(systemOrganizationExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.ORG10010001);
        }

        SystemOrganization systemOrganization = systemOrgFactory.convertDtoToDo(systemOrgDto,SystemOrganization.class);
        return systemOrganizationMapper.insertSelective(systemOrganization);
    }

    @Override
    public int updateDto(SystemOrgDto systemOrgDto) {
        //判断名称是否重复
        SystemOrganizationExample systemOrganizationExample=new SystemOrganizationExample();
        SystemOrganizationExample.Criteria criteria = systemOrganizationExample.createCriteria();
        criteria.andOrganizationNameEqualTo(systemOrgDto.getOrganizationName());
        criteria.andOrganizationIdNotEqualTo(systemOrgDto.getOrganizationId());
        criteria.andOrganizationPidEqualTo(1);
        List<SystemOrganization> list =systemOrganizationMapper.selectByExample(systemOrganizationExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.ORG10010001);
        }

        SystemOrganization systemOrganization = systemOrgFactory.convertDtoToDo(systemOrgDto,SystemOrganization.class);
        return systemOrganizationMapper.updateByPrimaryKey(systemOrganization);
    }

    @Override
    public List<SelectVo> selectForSelect() {
        List<SystemOrganization> systemOrganizationList =systemOrganizationMapper.selectByExample(new SystemOrganizationExample());
        List<SelectVo> selectVoList = systemOrgFactory .convertList(systemOrganizationList,SelectVo.class);

        return selectVoList;
    }

    @Override
    public List<SelectVo> selectForSelect(int userId) {
        SystemUserOrganizationExample systemUserOrganizationExample=new SystemUserOrganizationExample();
        systemUserOrganizationExample.createCriteria().andUserIdEqualTo(userId);
        List<SystemUserOrganization> systemOrganizationList =systemUserOrganizationMapper.selectByExample(systemUserOrganizationExample);
        List<SelectVo> selectVoList = systemOrgFactory .convertList(systemOrganizationList,SelectVo.class);

        return selectVoList;
    }

    @Override
    public void selectSubResult(int id) {
        SystemOrganizationExample systemOrganizationExample=new SystemOrganizationExample();
        SystemOrganizationExample.Criteria criteria = systemOrganizationExample.createCriteria();
        criteria.andOrganizationPidEqualTo(id);
        List<SystemOrganization> list =systemOrganizationMapper.selectByExample(systemOrganizationExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.ORG10010002);
        }
    }
}