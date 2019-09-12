package com.mtx.system.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.dao.dto.SystemConfigDto;
import com.mtx.system.dao.mapper.SystemConfigMapper;
import com.mtx.system.dao.model.SystemConfig;
import com.mtx.system.dao.model.SystemConfigExample;
import com.mtx.system.dao.vo.SystemConfigVo;
import com.mtx.system.rpc.api.SystemConfigService;
import com.mtx.system.rpc.factory.SystemConfigFactory;
import com.mtx.system.rpc.mapper.SystemConfigExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SystemConfigService实现
* Created by yu on 2018/12/27.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper, SystemConfig, SystemConfigExample> implements SystemConfigService {
    private SystemConfigFactory systemConfigFactory = new SystemConfigFactory();
    @Autowired
    SystemConfigMapper systemConfigMapper;
    @Autowired
    SystemConfigExtMapper systemConfigExtMapper;

    @Override
    public List<SystemConfigVo> list(Page<SystemConfig> page, SystemConfigDto systemConfigDto) {
        List<SystemConfigVo> list = systemConfigExtMapper.list(systemConfigDto,page.getOrderByField(),page.isAsc());
        return list;
    }

    @Override
    public SystemConfigVo selectByIdWithLeft(int configId) {
        SystemConfig systemConfig=systemConfigMapper.selectByPrimaryKey(configId);
        SystemConfigVo systemConfigVo = systemConfigFactory.convertToVo(systemConfig,SystemConfigVo.class);
        return systemConfigVo;
    }

    @Override
    public int insertDto(SystemConfigDto systemConfigDto) {
        //判断名称是否重复
        SystemConfigExample systemConfigExample=new SystemConfigExample();
        SystemConfigExample.Criteria criteria = systemConfigExample.createCriteria();
        criteria.andCodeEqualTo(systemConfigDto.getName());
        List<SystemConfig> list =systemConfigMapper.selectByExample(systemConfigExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.CONFIG10040001);
        }

        SystemConfig systemConfig = systemConfigFactory.convertDtoToDo(systemConfigDto,SystemConfig.class);
        return systemConfigMapper.insertSelective(systemConfig);
    }

    @Override
    public int updateDto(SystemConfigDto systemConfigDto) {
        //判断名称是否重复
        SystemConfigExample systemConfigExample=new SystemConfigExample();
        SystemConfigExample.Criteria criteria = systemConfigExample.createCriteria();
        criteria.andCodeEqualTo(systemConfigDto.getName());
        criteria.andConfigIdNotEqualTo(systemConfigDto.getConfigId());
        List<SystemConfig> list =systemConfigMapper.selectByExample(systemConfigExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.CONFIG10040001);
        }

        SystemConfig systemConfig = systemConfigFactory.convertDtoToDo(systemConfigDto,SystemConfig.class);
        int count=systemConfigMapper.updateByPrimaryKey(systemConfig);

        //刷新缓存
        GlobalProperties.me().refresh(PropertiesEnum.codeOf(systemConfig.getCode()));
        return count;

    }


}