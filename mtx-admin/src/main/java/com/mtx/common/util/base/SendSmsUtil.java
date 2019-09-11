package com.mtx.common.util.base;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mtx.common.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 短信相关
 */
@Slf4j
public class SendSmsUtil {

    /**
     * 生成6位验证码
     * @return
     */
    public static String getCaptcha() {
        String str = "0,1,2,3,4,5,6,7,8,9";
        String str2[] = str.split(",");// 将字符串以,分割
        Random rand = new Random();// 创建Random类的对象rand
        int index = 0;
        String randStr = "";// 创建内容为空字符串对象randStr
        randStr = "";// 清空字符串对象randStr中的值
        for (int i = 0; i < 6; ++i) {
            index = rand.nextInt(str2.length - 1);// 在0到str2.length-1生成一个伪随机数赋值给index
            randStr += str2[index];// 将对应索引的数组与randStr的变量值相连接
        }
        return randStr;
    }
    //发送
    public static SendSmsResponse sendSms(String phone, String code)  {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SystemConstant.ACCESS_KEY_ID, SystemConstant.ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", SystemConstant.MSG_PRODUCT, SystemConstant.MSG_DOMAIN);
        } catch (ClientException e) {
            log.error(e.getMessage(),e);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(SystemConstant.MSG_SIGN_NAME);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(SystemConstant.MSG_CAPTCHA_TEL_CODE);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam("{\'name\':\'Tom\', \'code\':\'"+code+"\'}");
        request.setTemplateParam("{\'code\':\'"+code+"\'}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(code);

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            log.error(e.getMessage(),e);
        }

        return sendSmsResponse;
    }
    //查询
    public static QuerySendDetailsResponse querySendDetails(String phone)  {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SystemConstant.ACCESS_KEY_ID, SystemConstant.ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", SystemConstant.MSG_PRODUCT, SystemConstant.MSG_DOMAIN);
        } catch (ClientException e) {
            log.error(e.getMessage(),e);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phone);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
            querySendDetailsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            log.error(e.getMessage(),e);
        }

        return querySendDetailsResponse;
    }



}
