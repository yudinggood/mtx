package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.dto.SystemConfigDto;
import com.mtx.system.dao.mapper.SystemConfigMapper;
import com.mtx.system.dao.model.SystemConfig;
import com.mtx.system.dao.model.SystemConfigExample;
import com.mtx.system.dao.vo.SystemConfigVo;

import java.util.List;

/**
* 降级实现SystemConfigService接口
* Created by yu on 2018/12/27.
*/
public class SystemConfigServiceMock extends BaseServiceMock<SystemConfigMapper, SystemConfig, SystemConfigExample> implements SystemConfigService {
    @Override
    public List<SystemConfigVo> list(Page<SystemConfig> page, SystemConfigDto systemConfigDto) {
        return null;
    }

    @Override
    public SystemConfigVo selectByIdWithLeft(int configId) {
        return null;
    }

    @Override
    public int insertDto(SystemConfigDto systemConfigDto) {
        return 0;
    }

    @Override
    public int updateDto(SystemConfigDto systemConfigDto) {
        return 0;
    }
}
