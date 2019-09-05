package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemPermissionVo implements Serializable {
    /*@Mapping("permissionId")  dozer字段相对应时，使用
    private int id;*/
    private static final long serialVersionUID = 1L;

    //id
    private Integer permissionId;
    //系统id
    private Integer systemId;
    //父id
    private Integer permissionPid;
    //排序
    private Integer permissionOrder;
    //权限名称
    private String name;
    //权限值
    private String permissionValue;
    //路径
    private String uri;
    //图标
    private String icon;
    //描述
    private String description;
    //权限类型0根1目录2菜单3按钮
    private Byte type;
    //状态0禁止1正常
    private Byte status;
    //菜单层级 0开始 没有则不是菜单
    private Byte menuLevel;
    //修改时间
    private Date editDate;
    //修改人
    private Integer editUser;


    //系统名称
    private String systemName;
    //父名称
    private String parentName;
    //修改或添加页面
    private String page;
    //权限类型0根1目录2菜单3按钮
    private String typeName;
    //状态0禁止1正常
    private String statusName;
}
