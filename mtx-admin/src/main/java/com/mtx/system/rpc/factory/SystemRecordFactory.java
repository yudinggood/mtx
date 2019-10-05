package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.DateUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.system.dao.vo.SystemRecordVo;
import com.mtx.system.dao.vo.TimeLineVo;

import java.util.Calendar;
import java.util.List;

public class SystemRecordFactory extends BaseFactory {
    private volatile static SystemRecordFactory singleton;
    public static SystemRecordFactory getInstance(){
        if(singleton==null){
            synchronized (SystemRecordFactory.class){
                if(singleton==null){
                    singleton=new SystemRecordFactory();
                }
            }
        }
        return singleton;
    }

    @Override
    protected <V, E> V convertAttribute(E e, V v) {

        return v;
    }

    public TimeLineVo convertTimeLine(List<SystemRecordVo> list) {
        Calendar cal = Calendar.getInstance();//当前
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.DAY_OF_MONTH,1);
        cal2.add(Calendar.DAY_OF_MONTH, -1);

        TimeLineVo timeLineVo = new TimeLineVo();
        for(SystemRecordVo systemRecordVo:list){
            systemRecordVo.setDateString(DateUtil.getFormat2(systemRecordVo.getEditDate()));
            if(Integer.parseInt(systemRecordVo.getDiffTime())==0){
                timeLineVo.getToday().add(systemRecordVo);
            }else if(Integer.parseInt(systemRecordVo.getDiffTime())==1){
                timeLineVo.getYesterday().add(systemRecordVo);
            }else if(Integer.parseInt(systemRecordVo.getDiffTime())< cal.get(Calendar.DAY_OF_WEEK)){
                timeLineVo.getThisWeek().add(systemRecordVo);
            }else if(Integer.parseInt(systemRecordVo.getDiffTime())< cal.get(Calendar.DAY_OF_WEEK)+7){
                timeLineVo.getLastWeek().add(systemRecordVo);
            }else if(Integer.parseInt(systemRecordVo.getDiffTime())<cal.get(Calendar.DAY_OF_MONTH)){
                timeLineVo.getThisMonth().add(systemRecordVo);
            }else if(Integer.parseInt(systemRecordVo.getDiffTime())<cal.get(Calendar.DAY_OF_MONTH)+cal2.get(Calendar.DAY_OF_MONTH)){
                timeLineVo.getLastMonth().add(systemRecordVo);
            }else {
                timeLineVo.getMore().add(systemRecordVo);
            }
        }
        return timeLineVo;
    }
}





