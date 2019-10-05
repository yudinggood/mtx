package com.mtx.system.dao.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class TimeLineVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SystemRecordVo> today = new ArrayList<>();
    private List<SystemRecordVo> yesterday = new ArrayList<>();
    private List<SystemRecordVo> thisWeek = new ArrayList<>();
    private List<SystemRecordVo> lastWeek = new ArrayList<>();
    private List<SystemRecordVo> thisMonth = new ArrayList<>();
    private List<SystemRecordVo> lastMonth = new ArrayList<>();
    private List<SystemRecordVo> more = new ArrayList<>();





}
