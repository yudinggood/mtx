package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemOrganizationVo implements Serializable {
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


    //父名称
    private String parentName;
    //修改或添加页面
    private String page;


}
