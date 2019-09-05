package com.mtx.system.server.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LogTest {
    @Test
    public void logTest(){
        log.error(String.format("11","22"));
        log.info(String.format("11","22"));
        log.warn(String.format("11","22"));

    }


}
