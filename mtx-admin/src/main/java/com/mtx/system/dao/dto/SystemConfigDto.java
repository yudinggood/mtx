package com.mtx.system.dao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class SystemConfigDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer configId;

    /**
     * 编码
     *
     * @mbg.generated
     */
    private String code;

    /**
     * 名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 值
     *
     * @mbg.generated
     */
    private String value;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     * 修改日期
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


    private String search;


}
