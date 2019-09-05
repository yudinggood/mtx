package com.mtx.common.util.exception;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 错误管理器
 *
 */
public class ErrorManager {
    //日志记录操作延时
    private final int OPERATE_DELAY_TIME = 10;
    //异步操作记录日志的线程池       指定延时后执行任务,把定时器放入其中
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    private ErrorManager() {
    }

    public static ErrorManager errorManager = new ErrorManager();

    public static ErrorManager me() {
        return errorManager;
    }

    public void executeLog(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
