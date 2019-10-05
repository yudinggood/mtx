package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseService;
import com.mtx.system.dao.model.SystemRecord;
import com.mtx.system.dao.model.SystemRecordExample;
import com.mtx.system.dao.vo.TimeLineVo;

/**
* SystemRecordService接口
* Created by yu on 2019/10/3.
*/
public interface SystemRecordService extends BaseService<SystemRecord, SystemRecordExample> {

    TimeLineVo timeline();
}