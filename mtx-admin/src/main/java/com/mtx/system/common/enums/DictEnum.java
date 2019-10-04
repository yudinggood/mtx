package com.mtx.system.common.enums;

public enum  DictEnum {
    /**
     * 权限类型
     */
    PERMISSION_TYPE("权限类型"),
    PERMISSION_STATE("权限状态"),
    SEX("性别"),
    USER_STATE("用户状态"),
    ERROR_TYPE("错误类型"),
    TASK_WAY("提醒方式"),




    ;


    private String name;

    DictEnum( java.lang.String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public static DictEnum codeOf(String code){
        for(DictEnum dict : DictEnum.values()){
            if(dict.name().equals(code)){
                return dict;
            }
        }
        return null;
    }
}
