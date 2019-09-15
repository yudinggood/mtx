package com.mtx.system.dao.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class SystemAttachVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer attachId;

    /**
     * 业务编码
     *
     * @mbg.generated
     */
    private String bizType;

    /**
     * 业务id
     *
     * @mbg.generated
     */
    private Integer bizId;

    /**
     * 文件名称
     *
     * @mbg.generated
     */
    private String fileName;

    /**
     * 存放路径
     *
     * @mbg.generated
     */
    private String filePath;

    /**
     * 后缀名
     *
     * @mbg.generated
     */
    private String suffix;

    /**
     * 新UUID名称
     *
     * @mbg.generated
     */
    private String newName;
    /**
     * 1.本地2.七牛云
     *
     * @mbg.generated
     */
    private Byte addressType;
    /**
     * 文件大小
     *
     * @mbg.generated
     */
    private Long fileSize;

    /**
     * 修改日期
     *
     * @mbg.generated
     */
    private Date editDate;

    /**
     * 修改人
     *
     * @mbg.generated
     */
    private Integer editUser;

    /**
     * 1是删除了
     *
     * @mbg.generated
     */
    private Byte deleted;

    //修改或添加页面
    private String page;
    //业务编码名称
    private String bizTypeName;
    //文件大小名称
    private String fileSizeName;
    //昵称
    private String nickName;

    //七牛云图片路径
    private String yunPath;
}
