package com.mtx.system.common.bean;

import com.mtx.common.spring.SpringContextUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.cache.Cache;
import com.mtx.common.util.cache.CacheKey;
import com.mtx.common.util.cache.CacheKit;
import com.mtx.system.common.enums.DictEnum;
import com.mtx.system.dao.vo.SystemDicVo;
import com.mtx.system.rpc.mapper.SystemDicExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 字典缓存工具类
 */
@Component
public class DictCacheKit {
    @Autowired
    SystemDicExtMapper systemDicExtMapper;
    public static DictCacheKit me() {
        return SpringContextUtil.getBean(DictCacheKit.class);
    }

    //根据字典code获取name
    public String getDictNameByCode(DictEnum dictEnum, String code){
        List<SystemDicVo> dicts = getBizDictByCode(dictEnum);
        return this.getDictNameByCode(dicts,code);
    }
    //根据字典name获取code
    public String getDictCodeByName(DictEnum dictEnum,String name){
        List<SystemDicVo> dicts = getBizDictByCode(dictEnum);
        return this.getDictCodeByName(dicts,name);
    }


    private String getDictCodeByName(List<SystemDicVo> dicts, String name){
        if(CollectionUtils.isEmpty(dicts)){
            return null;
        }
        for(SystemDicVo dict : dicts){
            if(dict.getDicName().equals(name)){
                return dict.getDicCode();
            }
        }
        return null;
    }
    private String getDictNameByCode(List<SystemDicVo> dicts, String code){
        if(ToolUtil.isOneEmpty(dicts,code)){
            return null;
        }
        for(SystemDicVo dict : dicts){
            if(dict.getDicCode().equals(code)){
                return dict.getDicName();
            }
        }
        return null;
    }

    public List<SystemDicVo> getBizDictByCode(DictEnum dictEnum){
        if(dictEnum == null){
            return null;
        }
        List<SystemDicVo> dicts = CacheKit.get(Cache.CONSTANT, CacheKey.BIZ_DICT_CODE + dictEnum.name());
        if(dicts == null){
            this.refresh(dictEnum);
            dicts = CacheKit.get(Cache.CONSTANT, CacheKey.BIZ_DICT_CODE + dictEnum.name());
        }
        return dicts;
    }

    public void refresh(DictEnum dictEnum){
        if(dictEnum == null){
            return ;
        }
        CacheKit.remove(Cache.CONSTANT,CacheKey.BIZ_DICT_CODE + dictEnum.name());
        List<SystemDicVo> dicts = systemDicExtMapper.selectForDic(dictEnum.name());
        CacheKit.put(Cache.CONSTANT, CacheKey.BIZ_DICT_CODE + dictEnum.name(),dicts);
    }
}
