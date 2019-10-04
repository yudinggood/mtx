package com.mtx.system.common.bean;

import com.mtx.system.dao.model.SystemRecord;
import com.mtx.system.rpc.api.SystemRecordService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: 任务执行类
 */

@Slf4j
@Component
public class QuartzJob implements Job {
	@Autowired
	SystemRecordService systemRecordService;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SystemRecord systemRecord =new SystemRecord();
		JobDataMap data = context.getJobDetail().getJobDataMap();
		try {
			systemRecord.setType((byte)2);
			systemRecord.setBizId(data.getInt("bizId"));
			systemRecord.setTitle(data.getString("title"));
			systemRecord.setDetail(data.getString("detail"));
			systemRecord.setEditUser(data.getInt("editUser"));
			systemRecord.setEditDate(new Date());
			systemRecordService.insert(systemRecord);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

}
