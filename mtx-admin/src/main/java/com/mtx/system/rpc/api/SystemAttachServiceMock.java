package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.mapper.SystemAttachMapper;
import com.mtx.system.dao.model.SystemAttach;
import com.mtx.system.dao.model.SystemAttachExample;
import com.mtx.system.dao.vo.SystemAttachVo;

import java.util.List;

/**
* 降级实现SystemAttachService接口
* Created by yu on 2018/12/27.
*/
public class SystemAttachServiceMock extends BaseServiceMock<SystemAttachMapper, SystemAttach, SystemAttachExample> implements SystemAttachService {
    @Override
    public List<SystemAttachVo> list(Page<SystemAttach> page, SystemAttachDto systemAttachDto) {
        return null;
    }

    @Override
    public SystemAttachVo selectByIdWithLeft(int attachId) {
        return null;
    }

    @Override
    public int insertDto(SystemAttachDto systemAttachDto) {
        return 0;
    }

    @Override
    public int insertDtoCloud(SystemAttachDto systemAttachDto) {
        return 0;
    }

    @Override
    public String[] selectForFilePaths(String ids) {
        return new String[0];
    }
}
