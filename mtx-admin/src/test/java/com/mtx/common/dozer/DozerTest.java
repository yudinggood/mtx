package com.mtx.common.dozer;

import com.mtx.system.dao.dto.SystemPermissionDto;
import com.mtx.system.dao.model.SystemPermission;
import com.mtx.system.dao.vo.SystemPermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DozerTest {
    //model转换用的
    Mapper mapper = new DozerBeanMapper();
    //利用注解转换
    @Test
    public void voObjectCopy(){
        SystemPermission sourceObject = new SystemPermission();
        sourceObject.setPermissionId(1234);
        SystemPermissionVo destObject =  mapper.map(sourceObject, SystemPermissionVo.class);
        log.debug(destObject.toString());
    }
    @Test
    public void dtoObjectCopy(){
        SystemPermissionDto sourceObject = new SystemPermissionDto();
        sourceObject.setPermissionId(12345);
        SystemPermission destObject =  mapper.map(sourceObject, SystemPermission.class);
        log.debug(destObject.toString());
    }
    //map转实体类
    @Test
    public void mapCopy(){
        Map<String,Object> map = new HashMap();
        map.put("permissionId", 10000L);
        SystemPermission destObject = mapper.map(map, SystemPermission.class);
        log.debug(destObject.toString());
    }

}



