package com.mtx.common.util.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 页面select 的vo
 */
@Data
public class SelectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;


}
