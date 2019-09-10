package com.mtx.system.server.test;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.mtx.common.util.base.SendSmsUtil;
import com.mtx.common.util.base.StringUtil;
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

    @Test
    public void index1() {
        SendSmsResponse sendSmsResponse = SendSmsUtil.sendSms("13625538625",SendSmsUtil.getCaptcha());
        log.debug(sendSmsResponse.toString());
    }

    @Test
    public void index2() {
        QuerySendDetailsResponse querySendDetailsResponse = SendSmsUtil.querySendDetails("13625538625");
        log.debug(querySendDetailsResponse.toString());
    }

    @Test
    public void index3() {
        log.info(StringUtil.getStringRandom());
    }

    @Test
    public void index() {

    }
}
