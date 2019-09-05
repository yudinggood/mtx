package com.mtx.system.common.enums;

public enum PropertiesEnum {


    /**
     * 系统首页
     */
    INDEX_PAGE("系统首页"),
    COMMON_INIT_PASSWORD("初始密码"),
    LOG_ASPECT("请求日志开关"),
    SYSTEM_VERSION("项目版本"),


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
