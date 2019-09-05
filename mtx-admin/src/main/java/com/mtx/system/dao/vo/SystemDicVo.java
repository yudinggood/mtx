package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemDicVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer dicId;

    /**
     * 父id
     *
     * @mbg.generated
     */
    private Integer dicPid;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Integer orders;

    /**
     * 字典编码
     *
     * @mbg.generated
     */
    private String dicCode;

    /**
     * 字典名称
     *
     * @mbg.generated
     */
    private String dicName;

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


    //修改或添加页面
    private String page;

}
