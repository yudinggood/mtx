package com.mtx.system.rpc.factory;

import com.mtx.common.util.factory.BaseFactory;
import com.mtx.system.dao.dto.SystemConfigDto;
import com.mtx.system.dao.model.SystemConfig;
import com.mtx.system.dao.vo.SystemConfigVo;

import java.util.Date;

public class SystemConfigFactory extends BaseFactory {
    @Override
    protected <V, E> V convertAttribute(E e, V v) {


        return v;
    }


    public SystemConfigVo convertToVo(SystemConfig systemConfig, Class<SystemConfigVo> systemConfigVoClass) {
        SystemConfigVo systemConfigVo =convertModel(systemConfig,systemConfigVoClass);
        return systemConfigVo;
    }

    public SystemConfig convertDtoToDo(SystemConfigDto systemConfigDto, Class<SystemConfig> systemConfigClass) {
        SystemConfig systemConfig = convertModel(systemConfigDto,systemConfigClass);
        systemConfig.setEditDate(new Date());
        systemConfig.setEditUser(1);
        return systemConfig;
    }
}
