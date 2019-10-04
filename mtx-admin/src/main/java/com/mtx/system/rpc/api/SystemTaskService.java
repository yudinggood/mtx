package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseService;
import com.mtx.system.dao.dto.SystemTaskDto;
import com.mtx.system.dao.model.SystemTask;
import com.mtx.system.dao.model.SystemTaskExample;
import com.mtx.system.dao.vo.SystemTaskVo;

import java.util.List;

/**
* SystemTaskService接口
* Created by yu on 2019/10/3.
*/
public interface SystemTaskService extends BaseService<SystemTask, SystemTaskExample> {

    SystemTaskVo selectByIdWithLeft(int taskId);

    int insertDto(SystemTaskDto systemTaskDto);

    List<SystemTaskVo> list(Page<SystemTask> page, SystemTaskDto systemTaskDto);

    int updateDto(SystemTaskDto systemTaskDto);
}