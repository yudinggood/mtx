package com.mtx.system.common.enums;
/**
 * 附件枚举
 */
public enum  AttachmentEnum {

    /**
     * 普通文件上传
     */
    COMMON_ATTACHMENT("普通文件上传"),
    HEAD_ATTACHMENT("头像上传"),




    ;



    private String name;

    AttachmentEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AttachmentEnum codeOf(String code){
        for(AttachmentEnum attachmentEnum : AttachmentEnum.values()){
            if(attachmentEnum.name().equals(code)){
                return attachmentEnum;
            }
        }
        return null;
    }
}
