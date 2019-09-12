package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseService;
import com.mtx.system.dao.dto.SystemConfigDto;
import com.mtx.system.dao.model.SystemConfig;
import com.mtx.system.dao.model.SystemConfigExample;
import com.mtx.system.dao.vo.SystemConfigVo;

import java.util.List;

/**
* SystemConfigService接口
* Created by yu on 2018/12/27.
*/
public interface SystemConfigService extends BaseService<SystemConfig, SystemConfigExample> {

    List<SystemConfigVo> list(Page<SystemConfig> page, SystemConfigDto systemConfigDto);

    SystemConfigVo selectByIdWithLeft(int configId);

    int insertDto(SystemConfigDto systemConfigDto);

    int updateDto(SystemConfigDto systemConfigDto);
}