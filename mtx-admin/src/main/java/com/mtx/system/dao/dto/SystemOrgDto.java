package com.mtx.system.dao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class SystemOrgDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer organizationId;

    /**
     * 父id
     *
     * @mbg.generated
     */
    private Integer organizationPid;

    /**
     * 组织名称
     *
     * @mbg.generated
     */
    private String organizationName;

    /**
     * 组织描述
     *
     * @mbg.generated
     */
    private String description;

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


}
