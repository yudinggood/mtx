package com.mtx.common.constant;

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

    public static final String LOG_LINE ="==========================================================================";
    public static final String EMPTY_STRING = " ";

    /** 系统模式--开发模式 */
    public static final String SYS_MODE_DEV = "development";
    /** 系统模式--测试模式 */
    public static final String SYS_MODE_TEST = "test";
    /** 系统模式--发布模式 */
    public static final String SYS_MODE_PUB = "production";

    public static final String ASC="asc";
    public static final String DESC="desc";

    public static final String SESSION_SECURITY_CODE = "sessionSecCode";//验证码

    public static final String SERVER = "server";
    public static final String CLIENT = "client";

    public static final String FORCE_LOGOUT = "FORCE_LOGOUT";//强制退出标记
    public static final String SESSION_SYSTEM_USER = "systemUser";//session中的systemUser属性

    //关于QQ三方登录的若干
    public static final String QQAPPID ="101464318";   //id
    public static final String QQCLIENT_SECRET ="780765751a2fb050dbb0b5e38c6eb1b4";   //secret
    public static final String QQAUTHPATH ="/qqAuthorization";   //回调地址




}
