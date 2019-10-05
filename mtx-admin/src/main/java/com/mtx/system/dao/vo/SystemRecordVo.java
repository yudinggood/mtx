package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemRecordVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer recordId;

    /**
     * 类型：1提醒配置2提醒内容
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     * 业务id
     *
     * @mbg.generated
     */
    private Integer bizId;

    /**
     * 标题
     *
     * @mbg.generated
     */
    private String title;

    /**
     * 详情
     *
     * @mbg.generated
     */
    private String detail;

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

    //日期差
    private String diffTime;
    //修改时间
    private String dateString ;


}






