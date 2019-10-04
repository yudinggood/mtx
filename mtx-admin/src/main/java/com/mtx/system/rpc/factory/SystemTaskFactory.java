package com.mtx.system.rpc.factory;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.RegularUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.system.dao.dto.SystemTaskDto;
import com.mtx.system.dao.model.SystemTask;
import com.mtx.system.dao.vo.SystemTaskVo;

import java.util.Date;
import java.util.List;

public class SystemTaskFactory extends BaseFactory {
    private volatile static SystemTaskFactory singleton;
    public static SystemTaskFactory getInstance(){
        if(singleton==null){
            synchronized (SystemTaskFactory.class){
                if(singleton==null){
                    singleton=new SystemTaskFactory();
                }
            }
        }
        return singleton;
    }

    @Override
    protected <V, E> V convertAttribute(E e, V v) {


        return v;
    }

    public SystemTaskVo convertToVo(SystemTask systemTask, Class<SystemTaskVo> systemTaskVoClass) {
        SystemTaskVo systemTaskVo =convertModel(systemTask,systemTaskVoClass);
        return systemTaskVo;
    }


    public SystemTask convertDtoToDo(SystemTaskDto systemTaskDto, Class<SystemTask> systemTaskClass) {
        //获取对应cron表达式
        this.handleTask(systemTaskDto);

        SystemTask systemTask = convertModel(systemTaskDto,systemTaskClass);
        systemTask.setEditDate(new Date());
        return systemTask;
    }

    private void handleTask(SystemTaskDto systemTaskDto) {
        switch (systemTaskDto.getType()){
            case 1:
                systemTaskDto.setTaskTime(systemTaskDto.getRemind_time_day());
                //每天的9:33提醒
                List<String> list= RegularUtil.getRegularByString("(\\d+):(\\d+)",systemTaskDto.getTaskTime());
                systemTaskDto.setCron("0 "+list.get(2)+" "+list.get(1)+" * * ?");
                break;

            case 5:
                systemTaskDto.setTaskTime(systemTaskDto.getRemind_time_day());
                List<String> list3= RegularUtil.getRegularByString("(\\d+):(\\d+)",systemTaskDto.getTaskTime());
                systemTaskDto.setCron("0 "+list3.get(2)+" "+list3.get(1)+" ? * MON-FRI");
                break;

            case 6:
                systemTaskDto.setTaskTime(systemTaskDto.getRemind_time_day());
                List<String> list2= RegularUtil.getRegularByString("(\\d+):(\\d+)",systemTaskDto.getTaskTime());
                systemTaskDto.setCron("0 "+list2.get(2)+" "+list2.get(1)+" ? * SAT-SUN");
                break;

            case 2:
                systemTaskDto.setTaskTime(systemTaskDto.getSelect_week()+ SystemConstant.EMPTY_STRING+systemTaskDto.getRemind_time_week());
                //每 SAT 10:44  提醒
                List<String> list4=RegularUtil.getRegularByString("([A-Z]{3}) (\\d+):(\\d+)",systemTaskDto.getTaskTime());
                systemTaskDto.setCron("0 "+list4.get(3)+" "+list4.get(2)+" ? * "+list4.get(1));
                break;

            case 3:
                systemTaskDto.setTaskTime(systemTaskDto.getRemind_time_month());
                //每月 28日 10:25  提醒
                List<String> list5=RegularUtil.getRegularByString("(\\d+)日 (\\d+):(\\d+)",systemTaskDto.getTaskTime());
                systemTaskDto.setCron("0 "+list5.get(3)+" "+list5.get(2)+" "+list5.get(1)+" * ?");
                break;

            default://4
                systemTaskDto.setTaskTime(systemTaskDto.getRemind_time_year());
                //每年 12-28 10:25 提醒
                List<String> list6=RegularUtil.getRegularByString("(\\d+)-(\\d+) (\\d+):(\\d+)",systemTaskDto.getTaskTime());
                systemTaskDto.setCron("0 "+list6.get(4)+" "+list6.get(3)+" "+list6.get(2)+" "+list6.get(1)+" ?");
        }



    }
}





