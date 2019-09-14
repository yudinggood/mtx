package com.mtx.system.common.enums;

public enum PropertiesEnum {


    /**
     * 系统首页
     */
    INDEX_PAGE("系统首页"),
    COMMON_INIT_PASSWORD("初始密码"),
    LOG_ASPECT("请求日志开关"),
    SYSTEM_VERSION("项目版本"),
    QQAPPID("QQ授权ID"),
    QQCLIENT_SECRET("QQ授权密钥"),
    ACCESS_KEY_ID("阿里云授权ID"),
    ACCESS_KEY_SECRET("阿里云授权密钥"),
    GIT_BOOK("文档域名"),
    FILE_ADDRESS_TYPE("文件保存的地方"),


    ;


    private String name;

    PropertiesEnum( java.lang.String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public static PropertiesEnum codeOf(String code){
        for(PropertiesEnum propertiesEnum : PropertiesEnum.values()){
            if(propertiesEnum.name().equals(code)){
                return propertiesEnum;
            }
        }
        return null;
    }

}
