package com.mtx.system.rpc.api;

import com.mtx.system.dao.model.SystemTask;

public class QuartzServiceMock implements QuartzService{
    @Override
    public void addJob(SystemTask systemTask, String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class cls, String cron) {

    }

    @Override
    public boolean modifyJobTime(SystemTask systemTask, String oldjobName, String oldjobGroup, String oldtriggerName, String oldtriggerGroup, String jobName, String jobGroup, String triggerName, String triggerGroup, String cron) {
        return false;
    }

    @Override
    public void modifyJobTime(String triggerName, String triggerGroupName, String cron) {

    }

    @Override
    public void pauseJob(String jobName, String jobGroupName) {

    }

    @Override
    public void resumeJob(String jobName, String jobGroupName) {

    }

    @Override
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {

    }

    @Override
    public void startSchedule() {

    }

    @Override
    public void shutdownSchedule() {

    }
}
