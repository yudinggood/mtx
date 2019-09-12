package com.mtx.system.common.bean;

import com.mtx.common.spring.SpringContextUtil;
import com.mtx.common.util.cache.Cache;
import com.mtx.common.util.cache.CacheKey;
import com.mtx.common.util.cache.CacheKit;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.dao.vo.SystemConfigVo;
import com.mtx.system.rpc.mapper.SystemConfigExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 项目全局配置
 */
@Component
public class GlobalProperties {
    @Autowired
    private Properties configProperties;
    @Autowired
    SystemConfigExtMapper systemConfigExtMapper;

    private Map<String, String> configMap;

    public static GlobalProperties me() {
        return SpringContextUtil.getBean(GlobalProperties.class);
    }

    @PostConstruct
    private void init() {//构造方法执行后，用来初始化的
        this.configMap = new HashMap<String, String>();
        Set<?> keys = this.configProperties.keySet();
        for (Iterator<?> localIterator = keys.iterator(); localIterator.hasNext();) {
            Object key = localIterator.next();
            this.configMap.put(String.valueOf(key), this.configProperties.getProperty(String.valueOf(key)));
        }
    }


    //根据字典code获取value  文件
    public String getValueByCodeProperties(PropertiesEnum propertiesEnum) {
        return (String) this.configMap.get(propertiesEnum.name());
    }

    //根据字典code获取value  后台
    public String getValueByCode(PropertiesEnum propertiesEnum){
        List<SystemConfigVo> dicts = getBizDictByCode(propertiesEnum);
        return dicts.get(0).getValue();
    }

    public List<SystemConfigVo> getBizDictByCode(PropertiesEnum propertiesEnum){
        if(propertiesEnum == null){
            return Collections.EMPTY_LIST;
        }
        List<SystemConfigVo> dicts = CacheKit.get(Cache.CONSTANT, CacheKey.BIZ_DICT_CODE + propertiesEnum.name());
        if(dicts == null){
            this.refresh(propertiesEnum);
            dicts = CacheKit.get(Cache.CONSTANT, CacheKey.BIZ_DICT_CODE + propertiesEnum.name());
        }
        return dicts;
    }

    public void refresh(PropertiesEnum propertiesEnum){
        if(propertiesEnum == null){
            return ;
        }
        CacheKit.remove(Cache.CONSTANT, CacheKey.BIZ_PROP_CODE + propertiesEnum.name());
        List<SystemConfigVo> dicts = systemConfigExtMapper.selectForDic(propertiesEnum.name());
        CacheKit.put(Cache.CONSTANT, CacheKey.BIZ_DICT_CODE + propertiesEnum.name(),dicts);
    }
}
