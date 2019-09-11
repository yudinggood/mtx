package com.mtx.common.util.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件处理类
 */
@Slf4j
public class FileUtil {
    /**
     * 根据文件绝对路径获取目录
     * @param filePath
     * @return
     */
    public static String getPath(String filePath) {
        String path = "";
        if (StringUtils.isNotBlank(filePath)) {
            path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return path;
    }

    /**
     * 根据文件绝对路径获取文件名
     * @param filePath
     * @return
     */
    public static String getFile(String filePath) {
        String file = "";
        if (StringUtils.isNotBlank(filePath)) {
            file = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return file;
    }

    // 递归删除文件夹
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    deleteDir(files[i]);
                }
            }

        }
        try {
            dir.delete();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }

    //获取txt里的配置信息
    public static String txtToString(){
        File parentDir=  new File(RequestUtil.getClassResources()+"config");
        File memoFile=new File(parentDir,"Banner.txt");
        String memo="";
        if(memoFile.exists()){
            try {
                memo= FileUtils.readFileToString(memoFile);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        return memo;
    }

    /**
     * 生成新文件名
     *
     * @param fileName
     * @return
     */
    public static String generateNewFileName(String fileName) {
        // uuid
        String uuid = ToolUtil.getUuid();
        // 文件后缀类型
        String fileType = fileName.substring(fileName.lastIndexOf("."),
                fileName.length());
        // 新文件名
        String newFileName = uuid + fileType;
        return newFileName;
    }

    /**
     * 生成文件相对路径
     *
     * @param fileName
     * @return
     */
    public static String generateFilePath(String fileName) {
        String path = DateUtil.getFormat(new Date());
        String filePath = path + "/" + fileName;
        return filePath;
    }

    /**
     * 生成文件
     *
     * @param filePath
     * @return File
     * @throws IOException
     */
    public static File generateFile(String filePath) throws IOException {
        File file = new File(filePath);
        // 文件路径
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 新建文件
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }
}
