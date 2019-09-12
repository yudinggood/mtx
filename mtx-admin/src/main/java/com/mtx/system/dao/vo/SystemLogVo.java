package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer logId;

    /**
     * 消耗时间
     *
     * @mbg.generated
     */
    private Integer usedTime;

    /**
     * 日志描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     * 根路径
     *
     * @mbg.generated
     */
    private String domain;

    /**
     * uri
     *
     * @mbg.generated
     */
    private String uri;

    /**
     * 请求类型
     *
     * @mbg.generated
     */
    private String method;

    /**
     * 用户标识
     *
     * @mbg.generated
     */
    private String userAgent;

    /**
     * 用户IP
     *
     * @mbg.generated
     */
    private String ip;

    /**
     * 权限值
     *
     * @mbg.generated
     */
    private String permission;

    /**
     * 操作人
     *
     * @mbg.generated
     */
    private Integer editUser;

    /**
     * 操作时间
     *
     * @mbg.generated
     */
    private Date editDate;

    /**
     * 1是删除了
     *
     * @mbg.generated
     */
    private Byte deleted;

    /**
     * 请求参数
     *
     * @mbg.generated
     */
    private String parameter;

    /**
     * 响应结果
     *
     * @mbg.generated
     */
    private String result;

    //昵称
    private String nickName;
}
