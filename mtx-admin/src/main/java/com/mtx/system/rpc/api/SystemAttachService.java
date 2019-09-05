package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseService;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.model.SystemAttach;
import com.mtx.system.dao.model.SystemAttachExample;
import com.mtx.system.dao.vo.SystemAttachVo;

import java.util.List;

/**
* SystemAttachService接口
* Created by yu on 2018/12/27.
*/
public interface SystemAttachService extends BaseService<SystemAttach, SystemAttachExample> {


    List<SystemAttachVo> list(Page<SystemAttach> page, SystemAttachDto systemAttachDto);

    SystemAttachVo selectByIdWithLeft(int attachId);

    int insertDto(SystemAttachDto systemAttachDto);

    String[] selectForFilePaths(String ids);
}