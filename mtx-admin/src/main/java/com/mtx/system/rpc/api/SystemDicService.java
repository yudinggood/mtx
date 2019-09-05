package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseService;
import com.mtx.system.dao.dto.SystemDicDto;
import com.mtx.system.dao.model.SystemDic;
import com.mtx.system.dao.model.SystemDicExample;
import com.mtx.system.dao.vo.SystemDicVo;

import java.util.List;

/**
* SystemDicService接口
* Created by yu on 2018/12/9.
*/
public interface SystemDicService extends BaseService<SystemDic, SystemDicExample> {

    String selectForTree(String keywords);

    String selectForTreeList(int dicId);

    SystemDicVo selectById(int dicId);

    int insertDto(SystemDicDto systemDicDto);

    List<SystemDicVo> selectByPid(int dicId);

    int updateDto(SystemDicDto systemDicDto);

    int deleteAll(int id);
}