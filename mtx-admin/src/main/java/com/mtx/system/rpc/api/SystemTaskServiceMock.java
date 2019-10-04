package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.dto.SystemTaskDto;
import com.mtx.system.dao.mapper.SystemTaskMapper;
import com.mtx.system.dao.model.SystemTask;
import com.mtx.system.dao.model.SystemTaskExample;
import com.mtx.system.dao.vo.SystemTaskVo;

import java.util.List;

/**
* 降级实现SystemTaskService接口
* Created by yu on 2019/10/3.
*/
public class SystemTaskServiceMock extends BaseServiceMock<SystemTaskMapper, SystemTask, SystemTaskExample> implements SystemTaskService {
    @Override
    public int insertDto(SystemTaskDto systemTaskDto) {
        return 0;
    }

    @Override
    public int updateDto(SystemTaskDto systemTaskDto) {
        return 0;
    }

    @Override
    public SystemTaskVo selectByIdWithLeft(int taskId) {
        return null;
    }

    @Override
    public List<SystemTaskVo> list(Page<SystemTask> page, SystemTaskDto systemTaskDto) {
        return null;
    }
}
