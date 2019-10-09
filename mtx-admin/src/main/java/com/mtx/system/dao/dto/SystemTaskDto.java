package com.mtx.system.dao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel
public class SystemTaskDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer taskId;

    /**
     * 名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 任务类型
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     * 任务状态 1提醒2不提醒
     *
     * @mbg.generated
     */
    private Byte state;

    /**
     * cron表达式
     *
     * @mbg.generated
     */
    private String cron;

    /**
     * 任务时间
     *
     * @mbg.generated
     */
    private String taskTime;
    /**
     * 任务执行类
     *
     * @mbg.generated
     */
    private String taskClass;
    /**
     * 修改时间
     *
     * @mbg.generated
     */
    private Date editDate;

    /**
     * 修改人
     *
     * @mbg.generated
     */
    private Integer editUser;

    /**
     * 1是删除了
     *
     * @mbg.generated
     */
    private Byte deleted;

    //日期相关属性
    private String remind_time_day;
    private String remind_time_year;
    private String remind_time_month;
    private String remind_time_week;
    private String select_week;

    private String search;




}
