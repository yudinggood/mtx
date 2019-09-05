package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.mapper.SystemSystemMapper;
import com.mtx.system.dao.model.SystemSystem;
import com.mtx.system.dao.model.SystemSystemExample;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* 降级实现SystemSystemService接口
* Created by yu on 2018/12/9.
*/
@Slf4j
public class SystemSystemServiceMock extends BaseServiceMock<SystemSystemMapper, SystemSystem, SystemSystemExample> implements SystemSystemService {
    @Override
    public List<SelectVo> selectForSelect() {
        log.debug("SystemSystemServiceMock => selectForSelect");
        return null;
    }

    @Override
    public SystemSystem selectByName(String name) {
        return null;
    }
}
