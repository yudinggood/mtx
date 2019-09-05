package com.mtx.system.common.exception;

public enum ErrorCodeEnum {
    /**
     * 99990401
     */
    SYS99990401(99990401, "无访问权限"),










    DIC10000001(10000001, "字典名称已经存在"),


    ORG10010001(10010001, "组织名称已经存在"),
    ORG10010002(10010002, "请先删除子组织"),


    ROLE10020001(10020001, "角色名称已经存在"),


    USER10030001(10030001, "用户手机号已经存在"),
    USER10030002(10030002, "用户邮箱已经存在"),


    CONFIG10040001(10040001, "配置编码已经存在"),


    INVALID_CODE(20000001, "验证码错误"),
    INVALID_USERNAME(20000002, "帐号不存在"),
    INVALID_PASSWORD(20000003, "密码错误"),
    INVALID_ACCOUNT(20000004, "帐号已锁定"),
    INVALID_UPMS_CODE(20000005, "无效upmsCode"),
    EXCESSIVE_ATTEMPTS(20000006, "登录失败次数超过5次,请10分钟后再试"),
    INVALID_USERNAME_PASSWORD(20000007, "帐号或密码错误"),

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
