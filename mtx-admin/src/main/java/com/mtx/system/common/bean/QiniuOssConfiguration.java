package com.mtx.system.common.bean;

import com.mtx.system.common.enums.PropertiesEnum;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class QiniuOssConfiguration {
    //由于代码发生 空指针错误 导致spring初始化失败
    /**
     * Auth auth.
     *
     * @return the auth
     */
    @Bean
    public Auth auth() {
        Auth auth = Auth.create(GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_ID),GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_SECRET));
        log.info("Create Auth OK.");
        return auth;
    }

    /**
     * Upload manager upload manager.
     *
     * @return the upload manager
     */
    @Bean
    public UploadManager uploadManager() {
        //创建上传对象
        UploadManager uploadManager = new UploadManager(new com.qiniu.storage.Configuration(Region.autoRegion()));
        log.info("Create UploadManager OK.");
        return uploadManager;
    }

    /**
     * Bucket manager bucket manager.
     *
     * @return the bucket manager
     */
    @Bean
    public BucketManager bucketManager() {
        //创建上传对象
        BucketManager uploadManager = new BucketManager(auth(), new com.qiniu.storage.Configuration(Region.autoRegion()));
        log.info("Create BucketManager OK.");
        return uploadManager;
    }
}
