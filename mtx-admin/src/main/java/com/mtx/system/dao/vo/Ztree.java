package com.mtx.system.dao.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.dozer.Mapping;

import java.io.Serializable;

@Data
@ApiModel
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Ztree implements Serializable {//@JsonAutoDetect  避免了大写变成小写
    private static final long serialVersionUID = 1L;

    //id
    @Mapping("permissionId")
    private Integer id;
    //父id
    @Mapping("permissionPid")
    private Integer pId;
    //路径
    private String uri;//如果是url，ztree会打开新的页面
    //图标
    private String icon;
    //权限名称
    private String name;
    //权限类型
    private String type;
    //是否打开
    //private Boolean open ;
    //是否选择
    private Boolean checked;
    //是否有选择框
    private Boolean nocheck;
}
