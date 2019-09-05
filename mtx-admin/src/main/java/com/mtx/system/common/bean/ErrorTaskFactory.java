package com.mtx.system.common.bean;

import com.mtx.common.spring.SpringContextUtil;
import com.mtx.system.dao.model.SystemError;
import com.mtx.system.rpc.api.SystemErrorService;
import com.mtx.system.rpc.factory.SystemLogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 */

@Component
@Slf4j
public class ErrorTaskFactory {
    @Autowired
    SystemErrorService systemErrorService;
    public static ErrorTaskFactory me() {
        return SpringContextUtil.getBean(ErrorTaskFactory.class);
    }

    //执行定时器
    public TimerTask exceptionLog(final Integer userId, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                SystemError systemError = SystemLogFactory.createErrorLog(userId,exception);
                try {
                    systemErrorService.insertSelective(systemError);
                } catch (Exception e) {
                    log.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
