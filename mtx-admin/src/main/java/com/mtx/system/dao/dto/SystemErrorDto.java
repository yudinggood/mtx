package com.mtx.system.dao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SystemErrorDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //错误类型
    private Integer errorType;
    private String search;
    //时间段
    private String editDate;
    private String startDate;
    private String endDate;
}
