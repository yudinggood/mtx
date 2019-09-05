package com.mtx.system.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class SystemRpcServiceApplication  {

    public static void main(String[] args) {
        log.info(">>>>> mtc-system-rpc-service 正在启动 <<<<<");
        new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        log.info(">>>>> mtc-system-rpc-service 启动完成 <<<<<");
    }


}
