package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.dto.SystemDicDto;
import com.mtx.system.dao.mapper.SystemDicMapper;
import com.mtx.system.dao.model.SystemDic;
import com.mtx.system.dao.model.SystemDicExample;
import com.mtx.system.dao.vo.SystemDicVo;

import java.util.List;

/**
* 降级实现SystemDicService接口
* Created by yu on 2018/12/9.
*/
public class SystemDicServiceMock extends BaseServiceMock<SystemDicMapper, SystemDic, SystemDicExample> implements SystemDicService {


    @Override
    public String selectForTree(String keywords) {
        return null;
    }

    @Override
    public String selectForTreeList(int dicId) {
        return null;
    }

    @Override
    public SystemDicVo selectById(int dicId) {
        return null;
    }

    @Override
    public int insertDto(SystemDicDto systemDicDto) {
        return 0;
    }

    @Override
    public List<SystemDicVo> selectByPid(int dicId) {
        return null;
    }

    @Override
    public int updateDto(SystemDicDto systemDicDto) {
        return 0;
    }

    @Override
    public int deleteAll(int id) {
        return 0;
    }
}
