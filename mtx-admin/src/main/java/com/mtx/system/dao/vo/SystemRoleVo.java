package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemRoleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Integer orders;

    /**
     * 角色名称
     *
     * @mbg.generated
     */
    private String roleName;

    /**
     * 角色编码
     *
     * @mbg.generated
     */
    private String roleCode;

    /**
     * 角色描述
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

    //修改或添加页面
    private String page;
    //昵称
    private String nickName;
}
