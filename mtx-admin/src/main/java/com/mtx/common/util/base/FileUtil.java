package com.mtx.common.util.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 文件处理类
 */
@Slf4j
public class FileUtil {
    private static final Map<String, String> FILE_TYPE_MAP = Maps.newHashMap();
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
     * 文件是否被占用，导致不能操做
     */
    public static boolean fileInUsed(File file){
        return file.renameTo(file);
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
    public static File generateFile(String filePath) {
        File file = new File(filePath);
        // 文件路径
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 新建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }

        return file;
    }
    /**
     * 判断文件类型是否正确
     */
    public static void checkFileType(String fileType) {
        String[] strings = fileType.split("/");

        // 获取文件类型
        FILE_TYPE_MAP.clear();
        // 上传的是图片
        if (FileTypeEnum.PICTURE.getType().equals(strings[0])) {
            getPicFileType();
            Preconditions.checkArgument(FILE_TYPE_MAP.containsKey(strings[1]), "图片格式非法");
        }
        // 上传的是音频
        if (FileTypeEnum.AUDIO.getType().equals(strings[0])) {
            getAudioFileType();
            Preconditions.checkArgument(FILE_TYPE_MAP.containsKey(strings[1]), "音频格式非法");
        }
        // 上传的是视频
        if (FileTypeEnum.VIDEO.getType().equals(strings[0])) {
            getVideoFileType();
            Preconditions.checkArgument(FILE_TYPE_MAP.containsKey(strings[1]), "视频格式非法");
        }
    }

    public enum FileTypeEnum {
        /**
         * 音频
         */
        AUDIO("audio", "音频"),
        /**
         * 图片
         */
        PICTURE("image", "图片"),
        /**
         * 视频
         */
        VIDEO("video", "视频");

        /**
         * The Type.
         */
        String type;
        /**
         * The Name.
         */
        String name;

        FileTypeEnum(String type, String name) {
            this.type = type;
            this.name = name;
        }

        /**
         * Gets type.
         *
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets name.
         *
         * @param type the type
         *
         * @return the name
         */
        public static String getName(String type) {
            for (FileTypeEnum ele : FileTypeEnum.values()) {
                if (ele.getType().equals(type)) {
                    return ele.getName();
                }
            }
            return null;
        }

        /**
         * Gets types.
         *
         * @return the types
         */
        public static List<String> getTypes() {
            List<String> list = new ArrayList<>();
            for (FileTypeEnum ele : FileTypeEnum.values()) {
                list.add(ele.getType());
            }
            return list;
        }

        /**
         * 获取List集合
         *
         * @return List list
         */
        public static List<FileTypeEnum> getList() {
            return Arrays.asList(FileTypeEnum.values());
        }
    }
    private static void getVideoFileType() {
        // Real Audio (ram)
        FILE_TYPE_MAP.put("ram", "2E7261FD");
        // Real Media (rm)
        FILE_TYPE_MAP.put("rm", "2E524D46");
        // Quicktime (mov)
        FILE_TYPE_MAP.put("mov", "00000014667479707174");
        // rmvb
        FILE_TYPE_MAP.put("rmvb", "2e524d46000000120001");
        FILE_TYPE_MAP.put("avi", "41564920");
        FILE_TYPE_MAP.put("avi", "52494646b440c02b4156");
        FILE_TYPE_MAP.put("flv", "464C5601050000000900");
        FILE_TYPE_MAP.put("mp4", "00000020667479706d70");
        FILE_TYPE_MAP.put("wmv", "3026b2758e66CF11a6d9");
        FILE_TYPE_MAP.put("3gp", "00000014667479703367");
        FILE_TYPE_MAP.put("mkv", "1a45dfa3010000000000");
    }

    private static void getPicFileType() {
        FILE_TYPE_MAP.put("jpeg", "FFD8FF");
        FILE_TYPE_MAP.put("png", "89504E47");
        FILE_TYPE_MAP.put("gif", "47494638");
        FILE_TYPE_MAP.put("bmp", "424D");
        FILE_TYPE_MAP.put("png", "89504E470D0a1a0a0000");
        // 16色位图(bmp)
        FILE_TYPE_MAP.put("bmp", "424d228c010000000000");
        // 24位位图(bmp)
        FILE_TYPE_MAP.put("bmp", "424d8240090000000000");
        // 256色位图(bmp
        FILE_TYPE_MAP.put("bmp", "424d8e1b030000000000");
    }

    private static void getAudioFileType() {
        // Wave (wav)
        FILE_TYPE_MAP.put("wav", "57415645");
        // MIDI (mid)
        FILE_TYPE_MAP.put("mid", "4D546864");
        FILE_TYPE_MAP.put("mp3", "49443303000000002176");
        FILE_TYPE_MAP.put("wav", "52494646e27807005741");
        FILE_TYPE_MAP.put("aac", "fff1508003fffcda004c");
        FILE_TYPE_MAP.put("wv", "7776706ba22100000704");
        FILE_TYPE_MAP.put("flac", "664c6143800000221200");
    }

    /**
     * url转MultipartFile，文件类型image/jpeg
     */
    public static MultipartFile createFileItem(String url) {
        FileItem item = null;
        OutputStream os = null;
        InputStream is =null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();

                FileItemFactory factory = new DiskFileItemFactory(16, null);
                String textFieldName = "uploadfile";
                item = factory.createItem(textFieldName, "image/jpeg", false, "qq_head.jpg");
                os = item.getOutputStream();

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }

        }

        return new CommonsMultipartFile(item);
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }
}
