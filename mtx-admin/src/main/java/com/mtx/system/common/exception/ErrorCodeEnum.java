package com.mtx.system.common.exception;

public enum ErrorCodeEnum {
    /**
     * 99990401
     */
    SYS99990401(99990401, "无访问权限"),
    SYS99990100(99990100, "文件已在java中打开暂时不能删除<br/>请稍后再试!"),









    DIC10000001(10000001, "字典名称已经存在"),


    ORG10010001(10010001, "组织名称已经存在"),
    ORG10010002(10010002, "请先删除子组织"),


    ROLE10020001(10020001, "角色名称已经存在"),


    USER10030001(10030001, "用户手机号已经存在"),
    USER10030002(10030002, "用户邮箱已经存在"),


    CONFIG10040001(10040001, "配置编码已经存在"),


    ATTACH10050001(10050001, "上传图片失败"),
    ATTACH10050002(10050002, "今日流量已用尽, 请明天再试"),


    INVALID_CODE(20000001, "验证码错误"),
    INVALID_USERNAME(20000002, "帐号不存在"),
    INVALID_PASSWORD(20000003, "密码错误"),
    INVALID_ACCOUNT(20000004, "帐号已锁定"),
    INVALID_UPMS_CODE(20000005, "无效upmsCode"),
    EXCESSIVE_ATTEMPTS(20000006, "登录失败次数超过5次,请10分钟后再试"),
    INVALID_USERNAME_PASSWORD(20000007, "帐号或密码错误"),
    INVALID_QQ_AUTHO(20000008, "QQ授权失败"),
    ACCOUNT_NOT_ACTIVE(20000009, "账号未激活"),
    ACCOUNT_IS_EXIST(20000010, "账号已存在"),
    SMS_ERROR(20000011, ",请联系管理员"),
    SMS_INFO_ERROR(20000012, "动态码信息错误,请重新发送"),
    SESSION_EXPIRED(20000013, "会话已经过期，请刷新页面"),



    ;

    private int code;
    private String message;

    public String message() {
        return message;
    }

    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
