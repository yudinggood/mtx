package com.mtx.system.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.db.DataSourceEnum;
import com.mtx.common.util.db.DynamicDataSource;
import com.mtx.system.common.enums.AttachmentEnum;
import com.mtx.system.common.file.UploadComponent;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.mapper.SystemAttachMapper;
import com.mtx.system.dao.model.SystemAttach;
import com.mtx.system.dao.model.SystemAttachExample;
import com.mtx.system.dao.vo.SystemAttachVo;
import com.mtx.system.rpc.api.SystemAttachService;
import com.mtx.system.rpc.factory.SystemAttachFactory;
import com.mtx.system.rpc.mapper.SystemAttachExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* SystemAttachService实现
* Created by yu on 2018/12/27.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemAttachServiceImpl extends BaseServiceImpl<SystemAttachMapper, SystemAttach, SystemAttachExample> implements SystemAttachService  {
    private SystemAttachFactory systemAttachFactory = new SystemAttachFactory();
    private UploadComponent uploadComponent = new UploadComponent();
    @Autowired
    SystemAttachMapper systemAttachMapper;
    @Autowired
    SystemAttachExtMapper systemAttachExtMapper;

    @Override
    public List<SystemAttachVo> list(Page<SystemAttach> page, SystemAttachDto systemAttachDto) {
        List<SystemAttachVo> list = systemAttachExtMapper.list(systemAttachDto,page,page.getOrderByField(),page.isAsc());
        List<SystemAttachVo> voList = systemAttachFactory.convertList(list,SystemAttachVo.class);
        return voList;
    }

    @Override
    public SystemAttachVo selectByIdWithLeft(int attachId) {
        SystemAttach systemAttach=systemAttachMapper.selectByPrimaryKey(attachId);
        SystemAttachVo systemAttachVo = systemAttachFactory.convertToVo(systemAttach,SystemAttachVo.class);
        return systemAttachVo;
    }

    @Override
    public int insertDto(SystemAttachDto systemAttachDto) {
        systemAttachDto =uploadComponent.upload(systemAttachDto);
        systemAttachDto.setBizType(AttachmentEnum.COMMON_ATTACHMENT.name());

        SystemAttach systemAttach = systemAttachFactory.convertDtoToDo(systemAttachDto,SystemAttach.class);
        return systemAttachMapper.insertSelective(systemAttach);
    }

    @Override
    public String[] selectForFilePaths(String ids) {
        List<String> strings=new ArrayList<>();
        if (StringUtils.isBlank(ids)) {
            return new String[0];
        }
        String[] idArray = ids.split("-");
        for (String idStr : idArray) {
            if (StringUtils.isBlank(idStr)) {
                continue;
            }
            Integer id = Integer.parseInt(idStr);
            SystemAttach systemAttach=systemAttachMapper.selectByPrimaryKey(id);
            strings.add(systemAttach.getFilePath());
        }
        return TypeConversionUtil.listToStrings(strings);
    }
}