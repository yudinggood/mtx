package com.mtx.common.constant;

import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.PropertiesEnum;

public class SystemConstant {
    public interface Number {//内部接口总是静态的
        int THOUSAND_INT = 1000;
        int HUNDRED_INT = 100;
        int ONE_INT = 1;
        int TWO_INT = 2;
        int THREE_INT = 3;
        int FOUR_INT = 4;
        int FIVE_INT = 5;
        int SIX_INT = 6;
        int SEVEN_INT = 7;
        int EIGHT_INT = 8;
        int NINE_INT = 9;
        int TEN_INT = 10;
    }
    //普通
    public static final String LOG_LINE ="==========================================================================";
    public static final String EMPTY_STRING = " ";

    /** 系统模式--开发模式 */
    public static final String SYS_MODE_DEV = "development";
    /** 系统模式--发布模式 */
    public static final String SYS_MODE_PUB = "production";

    public static final String ASC="asc";
    public static final String DESC="desc";

    public static final String SERVER = "server";
    public static final String CLIENT = "client";

    //登录相关
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";//验证码
    public static final String FORCE_LOGOUT = "FORCE_LOGOUT";//强制退出标记
    public static final String SESSION_SYSTEM_USER = "systemUser";//session中的systemUser属性
    public static final String COMMON_ROLE = "common";//表示普通用户
    public static final String COMMON_ROLE_ZH = "普通用户";//表示普通用户 中文
    // 局部会话key
    public final static String UPMS_CLIENT_SESSION_ID = "upms-client-session-id";
    // 单点同一个code所有局部会话key
    public final static String UPMS_CLIENT_SESSION_IDS = "upms-client-session-ids";
    //当前登录类型
    public static final String UPMS_TYPE = "upms.type";

    //关于QQ三方登录的若干
    public static final String QQAPPID = GlobalProperties.me().getValueByCode(PropertiesEnum.QQAPPID);   //id
    public static final String QQCLIENT_SECRET =GlobalProperties.me().getValueByCode(PropertiesEnum.QQCLIENT_SECRET);   //secret
    public static final String QQAUTHPATH ="/qqAuthorization";   //回调地址
    public static final String UPMS_WITHOUT_PASSWORD = "upms_without_password";

    //阿里云短信
    public static final String ACCESS_KEY_ID = GlobalProperties.me().getValueByCode(PropertiesEnum.ACCESS_KEY_ID);
    public static final String ACCESS_KEY_SECRET = GlobalProperties.me().getValueByCode(PropertiesEnum.ACCESS_KEY_SECRET);
    public static final String MSG_PRODUCT = "Dysmsapi";
    public static final String MSG_DOMAIN = "dysmsapi.aliyuncs.com";
    public static final String MSG_SIGN_NAME = "i助理";
    public static final String MSG_CAPTCHA_TEL_CODE = "SMS_173760206";//


}
