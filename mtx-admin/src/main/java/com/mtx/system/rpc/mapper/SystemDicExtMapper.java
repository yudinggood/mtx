package com.mtx.system.rpc.mapper;


import com.mtx.system.dao.model.SystemDic;
import com.mtx.system.dao.vo.SystemDicVo;

import java.util.List;

public interface SystemDicExtMapper {


	List<SystemDic> selectForTreeList(int dicId);

    List<SystemDic> selectForTree(String keywords);

    int batchInsert(List<SystemDic> list);

    void batchDelete(List<SystemDicVo> list);

    List<SystemDicVo> selectForDic(String code);
}
