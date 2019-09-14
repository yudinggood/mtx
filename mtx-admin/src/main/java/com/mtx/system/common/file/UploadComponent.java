package com.mtx.system.common.file;

import com.google.common.base.Preconditions;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.FileUtil;
import com.mtx.common.util.base.PropertiesFileUtil;
import com.mtx.system.dao.dto.SystemAttachDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

@Slf4j
public class UploadComponent {
    private static final String DEFAULT_ATTACHMENT_DIR = PropertiesFileUtil.getInstance("base").get("upload.path");

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

    public SystemAttachDto upload(SystemAttachDto systemAttachDto)  {
        MultipartFile file =systemAttachDto.getFile();
        Preconditions.checkArgument(file.getSize() <= SystemConstant.FILE_MAX_SIZE, "上传文件不能大于5M");
        FileUtil.checkFileType(file.getContentType());//判断文件类型是否正确

        String fileName = file.getOriginalFilename();// 原文件名
        String newName = FileUtil.generateNewFileName(fileName);
        String filePath = FileUtil.generateFilePath(newName);
        File destFile = FileUtil.generateFile(getAttachmentDir()+"/"+filePath);//绝对路径
        //file.transferTo(destFile);// 复制文件
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            inputStream = file.getInputStream();
            out = new FileOutputStream(destFile);
            IOUtils.copy(inputStream, out);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } finally {
            try {
                inputStream.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        systemAttachDto.setEditDate(new Date());
        systemAttachDto.setFileName(fileName);
        systemAttachDto.setFilePath(filePath);
        systemAttachDto.setFileSize(file.getSize());
        systemAttachDto.setSuffix(file.getContentType());
        systemAttachDto.setNewName(newName);

        return systemAttachDto;
    }



    /**
     * 返回绝对路径
     *
     * @param filePath
     * @return
     */

    public String getAbsolutePath(String filePath) {
        // 绝对路径
        return getAttachmentDir() + "/" + filePath;
    }
    public static String getAbsolutePaths(String filePath) {
        // 绝对路径
        return UploadComponent.DEFAULT_ATTACHMENT_DIR + "/" + filePath;
    }

    //删除文件
    public void delete(String filePath) {
        // file路径
        String destFilePath = getAbsolutePath(filePath);
        File file = new File(destFilePath);
        // 除去后缀的文件名
        String fileName = file.getPath().substring(0,destFilePath.lastIndexOf("."));
        File parentFile = file.getParentFile();
        if (parentFile.isDirectory()) {
            File[] files = parentFile.listFiles(new DefaultFilenameFilter(fileName));
            for (File src : files) {
                if (src != null && src.exists()) {
                    src.delete();
                } else {
                    log.warn(" delete 文件不存在！file is {}", src.getPath());
                }
            }
        }else{
            if (file != null && file.exists()) {
                file.delete();
            } else {
                log.warn(" delete 文件不存在！file is {}", file.getPath());
            }
        }
    }
    /**
     * 默认文件名过滤
     *
     *
     */
    private class DefaultFilenameFilter implements FilenameFilter {

        private String fileName;

        public DefaultFilenameFilter(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public boolean accept(File file, String path) {
            String absolutePath = file.getPath()+ File.separator  + path;
            if (absolutePath.startsWith(fileName)) {
                return true;
            }

            return false;
        }

    }

}
