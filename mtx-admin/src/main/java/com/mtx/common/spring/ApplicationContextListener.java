package com.mtx.common.spring;

import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseInterface;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.FileUtil;
import com.mtx.common.util.base.PropertiesFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

//spring容器初始化完成事件
@Slf4j
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // root application context
        if(null == contextRefreshedEvent.getApplicationContext().getParent()) {
            log.debug(">>>>> spring初始化完毕 <<<<<");
            // spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
            Map<String, Object> baseServices = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseService.class);
            for(Object service : baseServices.values()) {
                log.debug(">>>>> {}.initMapper()", service.getClass().getName());
                try {
                    Method initMapper = service.getClass().getMethod("initMapper");
                    initMapper.invoke(service);
                } catch (Exception e) {
                    log.error("初始化BaseService的initMapper方法异常", e);
                }
            }

            // 系统入口初始化
            Map<String, BaseInterface> baseInterfaceBeans = contextRefreshedEvent.getApplicationContext().getBeansOfType(BaseInterface.class);
            for(Object service : baseInterfaceBeans.values()) {
                log.debug(">>>>> {}.init()", service.getClass().getName());
                try {
                    Method init = service.getClass().getMethod("init");
                    init.invoke(service);
                } catch (Exception e) {
                    log.error("初始化BaseInterface的init方法异常", e);
                }
            }

            //启动成功提示
            log.info("\n"+ SystemConstant.LOG_LINE+ FileUtil.txtToString()+
                    PropertiesFileUtil.getInstance("base").get("system.version")+
                    "\n"+SystemConstant.LOG_LINE);

        }
    }
}
