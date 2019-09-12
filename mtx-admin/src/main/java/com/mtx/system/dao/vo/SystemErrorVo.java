package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemErrorVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer errorId;

    /**
     * 错误类型1登录日志2异常日志3业务异常日志
     *
     * @mbg.generated
     */
    private Byte errorType;

    /**
     * 类
     *
     * @mbg.generated
     */
    private String className;

    /**
     * 方法
     *
     * @mbg.generated
     */
    private String method;

    /**
     * 用户
     *
     * @mbg.generated
     */
    private Integer editUser;

    /**
     * 修改时间
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
     * 编码
     *
     * @mbg.generated
     */
    private String code;

    /**
     * 信息
     *
     * @mbg.generated
     */
    private String message;

    //昵称
    private String nickName;
}
