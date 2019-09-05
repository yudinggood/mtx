package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseService;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.model.SystemSystem;
import com.mtx.system.dao.model.SystemSystemExample;

import java.util.List;

/**
* SystemSystemService接口
* Created by yu on 2018/12/9.
*/
public interface SystemSystemService extends BaseService<SystemSystem, SystemSystemExample> {

    List<SelectVo> selectForSelect();
    SystemSystem selectByName(String name);
}