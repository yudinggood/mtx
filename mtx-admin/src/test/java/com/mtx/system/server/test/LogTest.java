package com.mtx.system.server.test;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.mtx.common.util.base.FileUtil;
import com.mtx.common.util.base.SendSmsUtil;
import com.mtx.common.util.base.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

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
    public void index4() {
        log.info(StringUtil.getParam("access_token=40A5BBC651DCB172D2C0EE31E31BC8D7&expires_in=7776000&refresh_token=8186D0AEB425AD829DA52375E9F4B7DE","access_token"));
    }

    @Test
    public void index5() {
        String url = "http://qzapp.qlogo.cn/qzapp/101464318/245835C0F2923CF468B71CD6F2CAE88C/100";
        MultipartFile file = FileUtil.createFileItem(url);
        System.out.print(file);
    }



    @Test
    public void index() {

    }
}






