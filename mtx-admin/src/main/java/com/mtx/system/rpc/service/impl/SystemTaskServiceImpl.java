package com.mtx.system.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.RegularUtil;
import com.mtx.system.common.task.QuartzJob;
import com.mtx.system.dao.dto.SystemTaskDto;
import com.mtx.system.dao.mapper.SystemTaskMapper;
import com.mtx.system.dao.model.SystemTask;
import com.mtx.system.dao.model.SystemTaskExample;
import com.mtx.system.dao.vo.SystemTaskVo;
import com.mtx.system.rpc.api.QuartzService;
import com.mtx.system.rpc.api.SystemTaskService;
import com.mtx.system.rpc.factory.SystemTaskFactory;
import com.mtx.system.rpc.mapper.SystemTaskExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SystemTaskService实现
* Created by yu on 2019/10/3.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemTaskServiceImpl extends BaseServiceImpl<SystemTaskMapper, SystemTask, SystemTaskExample> implements SystemTaskService {

    @Autowired
    SystemTaskMapper systemTaskMapper;
    @Autowired
    SystemTaskExtMapper systemTaskExtMapper;
    @Autowired
    QuartzService quartzService;

    @Override
    public SystemTaskVo selectByIdWithLeft(int taskId) {
        SystemTask systemTask=systemTaskMapper.selectByPrimaryKey(taskId);
        SystemTaskVo systemTaskVo = SystemTaskFactory.getInstance().convertToVo(systemTask,SystemTaskVo.class);
        if(null!=systemTaskVo.getType()&&systemTaskVo.getType()==2){
            List<String> list= RegularUtil.getRegularByString("([A-Z]{3}) (\\d+:\\d+)",systemTaskVo.getTaskTime());
            systemTaskVo.setSelectWeek(list.get(1));
            systemTaskVo.setRemindTimeWeek(list.get(2));
        }
        return systemTaskVo;
    }

    @Override
    public int insertDto(SystemTaskDto systemTaskDto) {
        SystemTask systemTask = SystemTaskFactory.getInstance().convertDtoToDo(systemTaskDto,SystemTask.class);
        int count= systemTaskMapper.insertSelective(systemTask);

        //添加任务到quartz
        if(systemTask.getState()==1){
            quartzService.addJob(systemTask,systemTask.getTaskId()+ SystemConstant.REMIND_TASK_GROUP,
                    SystemConstant.REMIND_TASK_GROUP,
                    systemTask.getTaskId()+ SystemConstant.REMIND_TRIGGER_GROUP,
                    SystemConstant.REMIND_TRIGGER_GROUP,
                    QuartzJob.class,systemTask.getCron());
        }

        return count;
    }

    @Override
    public List<SystemTaskVo> list(Page<SystemTask> page, SystemTaskDto systemTaskDto) {
        List<SystemTaskVo> list = systemTaskExtMapper.list(systemTaskDto,page.getOrderByField(),page.isAsc());
        return list;
    }

    @Override
    public int updateDto(SystemTaskDto systemTaskDto) {
        SystemTask oldTask = systemTaskMapper.selectByPrimaryKey(systemTaskDto.getTaskId());
        SystemTask systemTask = SystemTaskFactory.getInstance().convertDtoToDo(systemTaskDto,SystemTask.class);
        int count=systemTaskMapper.updateByPrimaryKey(systemTask);

        //修改quartz任务
        if(oldTask.getState()==1&&systemTask.getState()==2){//暂停
            quartzService.pauseJob(systemTask.getTaskId()+SystemConstant.REMIND_TASK_GROUP,SystemConstant.REMIND_TASK_GROUP);
        }else if(oldTask.getState()==2&&systemTask.getState()==1){//继续
            quartzService.resumeJob(systemTask.getTaskId()+SystemConstant.REMIND_TASK_GROUP,SystemConstant.REMIND_TASK_GROUP);
        }else if(!oldTask.getCron().equals(systemTask.getCron())||!oldTask.getName().equals(systemTask.getName())){//修改
            quartzService.modifyJobTime(systemTask,oldTask.getTaskId()+SystemConstant.REMIND_TASK_GROUP,
                    SystemConstant.REMIND_TASK_GROUP,oldTask.getTaskId()+SystemConstant.REMIND_TRIGGER_GROUP,
                    SystemConstant.REMIND_TRIGGER_GROUP,
                    systemTask.getTaskId()+SystemConstant.REMIND_TASK_GROUP,
                    SystemConstant.REMIND_TASK_GROUP,
                    systemTask.getTaskId()+SystemConstant.REMIND_TRIGGER_GROUP,
                    SystemConstant.REMIND_TRIGGER_GROUP,systemTask.getCron());
        }

        return count;
    }

}