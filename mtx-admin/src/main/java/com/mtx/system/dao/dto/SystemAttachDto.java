package com.mtx.system.dao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class SystemAttachDto implements Serializable {
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

    //
    private String search;
    //时间段
    private String startDate;
    private String endDate;
    //上传的文件
    private MultipartFile file;


}
