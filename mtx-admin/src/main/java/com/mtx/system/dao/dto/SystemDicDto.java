package com.mtx.system.dao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SystemDicDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //父名称
    private String dicName;
    //父编码
    private String dicCode;
    //子字典
    private String dictValues;
    //id
    private Integer dicId;


}
