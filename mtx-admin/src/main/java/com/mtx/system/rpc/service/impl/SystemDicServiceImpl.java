package com.mtx.system.rpc.service.impl;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.JsonUtil;
import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.system.common.bean.DictCacheKit;
import com.mtx.system.common.enums.DictEnum;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.dao.dto.SystemDicDto;
import com.mtx.system.dao.mapper.SystemDicMapper;
import com.mtx.system.dao.model.SystemDic;
import com.mtx.system.dao.model.SystemDicExample;
import com.mtx.system.dao.vo.SystemDicVo;
import com.mtx.system.dao.vo.Ztree;
import com.mtx.system.rpc.api.SystemDicService;
import com.mtx.system.rpc.factory.SystemDicFactory;
import com.mtx.system.rpc.mapper.SystemDicExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* SystemDicService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemDicServiceImpl extends BaseServiceImpl<SystemDicMapper, SystemDic, SystemDicExample> implements SystemDicService {
    private SystemDicFactory systemDicFactory=new SystemDicFactory();
    @Autowired
    SystemDicMapper systemDicMapper;
    @Autowired
    SystemDicExtMapper systemDicExtMapper;

    @Override
    public String selectForTree(String keywords) {
        List<SystemDic> list=systemDicExtMapper.selectForTree(keywords);
        List<Ztree> voList= systemDicFactory.convertList(list,Ztree.class);
        String json = null;
        try {
            json = JsonUtil.toJson(voList);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return json;
    }

    @Override
    public String selectForTreeList(int dicId) {
        if(dicId==0){
            return null;
        }
        List<SystemDic> list= systemDicExtMapper.selectForTreeList(dicId);
        String json = null;
        try {
            json = JsonUtil.toJson(list);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return json;
    }

    @Override
    public SystemDicVo selectById(int dicId) {
        if(dicId==0){
            return new SystemDicVo();
        }
        SystemDic systemDic=systemDicMapper.selectByPrimaryKey(dicId);
        return systemDicFactory.convertModel(systemDic,SystemDicVo.class);
    }

    @Override
    public int insertDto(SystemDicDto systemDicDto) {
        //判断名称是否重复
        SystemDicExample systemDicExample=new SystemDicExample();
        SystemDicExample.Criteria criteria = systemDicExample.createCriteria();
        criteria.andDicNameEqualTo(systemDicDto.getDicName());
        List<SystemDic> list =systemDicMapper.selectByExample(systemDicExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.DIC10000001);
        }
        //加入父字典
        List<Map<String, String>> items = StringUtil.parseKeyValue(systemDicDto.getDictValues());
        SystemDic systemDic = systemDicFactory.convertParent(systemDicDto);
        systemDicMapper.insert(systemDic);
        //批量插入子字典
        List<SystemDic> subList = new ArrayList<>();
        for (Map<String, String> item : items) {
            SystemDic itemDict = new SystemDic();
            itemDict.setDicPid(systemDic.getDicId());
            itemDict.setEditDate(new Date());
            itemDict.setDicName(item.get(StringUtil.MUTI_STR_VALUE));
            itemDict.setDicCode(item.get(StringUtil.MUTI_STR_KEY));
            subList.add(itemDict);
        }
        if(ToolUtil.isEmpty(subList)){
            return 1;
        }else {
            return systemDicExtMapper.batchInsert(subList);
        }

    }

    @Override
    public List<SystemDicVo> selectByPid(int dicId) {
        SystemDicExample systemDicExample=new SystemDicExample();
        SystemDicExample.Criteria criteria = systemDicExample.createCriteria();
        criteria.andDicPidEqualTo(dicId);
        List<SystemDic> list=systemDicMapper.selectByExample(systemDicExample);
        return systemDicFactory.convertListDozer(list,SystemDicVo.class);
    }

    @Override
    public int updateDto(SystemDicDto systemDicDto) {
        int count=0;
        //判断名称是否重复
        SystemDicExample systemDicExample=new SystemDicExample();
        SystemDicExample.Criteria criteria = systemDicExample.createCriteria();
        criteria.andDicNameEqualTo(systemDicDto.getDicName());
        criteria.andDicIdNotEqualTo(systemDicDto.getDicId());
        List<SystemDic> list =systemDicMapper.selectByExample(systemDicExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.DIC10000001);
        }
        //修改父字典
        SystemDic systemDic = systemDicFactory.convertParent(systemDicDto);
        systemDicMapper.updateByPrimaryKeySelective(systemDic);
        //先批量删除子字典，再批量添加
        List<SystemDicVo> plist = selectByPid(systemDic.getDicId());
        if(!ToolUtil.isEmpty(plist)){
            systemDicExtMapper.batchDelete(plist);
        }

        List<Map<String, String>> items = StringUtil.parseKeyValue(systemDicDto.getDictValues());
        List<SystemDic> subList = new ArrayList<>();
        for (Map<String, String> item : items) {
            SystemDic itemDict = new SystemDic();
            itemDict.setDicPid(systemDic.getDicId());
            itemDict.setEditDate(new Date());
            itemDict.setDicName(item.get(StringUtil.MUTI_STR_VALUE));
            itemDict.setDicCode(item.get(StringUtil.MUTI_STR_KEY));
            subList.add(itemDict);
        }
        if(ToolUtil.isEmpty(subList)){
            count = 1;
        }else {
            count = systemDicExtMapper.batchInsert(subList);
        }
        //刷新缓存
        DictCacheKit.me().refresh(DictEnum.codeOf(systemDicDto.getDicCode()));
        return count;
    }

    @Override
    public int deleteAll(int id) {
        int count=systemDicMapper.deleteByPrimaryKey(id);
        List<SystemDicVo> plist = selectByPid(id);
        if(!ToolUtil.isEmpty(plist)){
            systemDicExtMapper.batchDelete(plist);
        }
        return count;
    }
}