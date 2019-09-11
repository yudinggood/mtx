package com.mtx.system.common.file;

import com.mtx.common.util.base.FileUtil;
import com.mtx.common.util.base.PropertiesFileUtil;
import com.mtx.system.dao.dto.SystemAttachDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class UploadComponent {
    private final String DEFAULT_ATTACHMENT_DIR = PropertiesFileUtil.getInstance("base").get("upload.path");

    private String attachmentDir;

    public UploadComponent() {
        this.attachmentDir = DEFAULT_ATTACHMENT_DIR;
    }

    public UploadComponent(String attachmentDir) {
        if(attachmentDir == null){
            this.attachmentDir = DEFAULT_ATTACHMENT_DIR;
        }
        this.attachmentDir = FilenameUtils.normalizeNoEndSeparator(attachmentDir);
    }

    public String getAttachmentDir() {
        return attachmentDir;
    }

    public SystemAttachDto upload(MultipartFile file)  {
        String fileName = file.getOriginalFilename();// 原文件名
        Long fileSize = file.getSize();
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        String newName = FileUtil.generateNewFileName(fileName);
        String filePath = FileUtil.generateFilePath(newName);
        File destFile = null;//绝对路径
        try {
            destFile = FileUtil.generateFile(getAttachmentDir()+"/"+filePath);
            file.transferTo(destFile);// 复制文件
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        SystemAttachDto systemAttachDto = SystemAttachDto.builder().editDate(new Date()).fileName(fileName)
                .filePath(filePath).fileSize(fileSize).suffix(file.getContentType()).newName(newName).build();
        return systemAttachDto;
    }



}
