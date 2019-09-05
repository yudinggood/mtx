package com.mtx.common.util.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Slf4j
public class AdminUtil implements InitializingBean, ServletContextAware {
    @Override
    public void afterPropertiesSet() throws Exception {


    }

    @Override
    public void setServletContext(ServletContext servletContext) {//一般用于项目启动时执行这个方法
        log.info("===== 开始解压mtx-ui ====");
        String jarPath = servletContext.getRealPath("/WEB-INF/lib/mtx-ui-" + "1.0" + ".jar");
        log.info("mtx-ui.jar 包路径: {}", jarPath);
        String resources = servletContext.getRealPath("/") + "/resources/mtx-admin";
        log.info("mtx-ui.jar 解压到: {}", resources);
        JarUtil.decompress(jarPath, resources);
        log.info("===== 解压mtx-ui完成 ====");
    }
}
