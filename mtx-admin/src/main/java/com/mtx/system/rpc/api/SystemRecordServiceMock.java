package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.mapper.SystemRecordMapper;
import com.mtx.system.dao.model.SystemRecord;
import com.mtx.system.dao.model.SystemRecordExample;

/**
* 降级实现SystemRecordService接口
* Created by yu on 2019/10/3.
*/
public class SystemRecordServiceMock extends BaseServiceMock<SystemRecordMapper, SystemRecord, SystemRecordExample> implements SystemRecordService {

}
