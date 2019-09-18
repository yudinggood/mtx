package com.mtx.system.server.system;

import com.mtx.common.spring.SpringContextUtil;
import com.mtx.system.rpc.api.SystemPermissionService;
import com.mtx.system.server.BaseJunit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

//系统业务单元测试
@Slf4j
@SuppressWarnings("SpringJavaAutowiringInspection")
public class SystemTest extends BaseJunit{
    @Autowired
    private SystemPermissionService systemPermissionService;

    @Test
    public void index() {
        Map map = systemPermissionService.selectForMenu();
        Assert.assertNotNull(map);//断言null则抛出异常  ，顺我者昌逆我者亡 原则
        log.debug(String.valueOf(map));
    }

    @Test
    public void index2() {
        SpringContextUtil.printAllBeans();
    }
}
