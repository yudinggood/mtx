package com.mtx.system.common.bean;

import com.mtx.system.common.enums.PropertiesEnum;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class QiniuOssConfiguration {

    /**
     * Auth auth.
     *
     * @return the auth
     */
    @Bean
    public Auth auth() {
        Auth auth = Auth.create(GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_ID),
                GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_SECRET));
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
        Zone zone = Zone.autoZone();
        //创建上传对象
        UploadManager uploadManager = new UploadManager(new com.qiniu.storage.Configuration(zone));
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
        Zone zone = Zone.autoZone();
        //创建上传对象
        BucketManager uploadManager = new BucketManager(auth(), new com.qiniu.storage.Configuration(zone));
        log.info("Create BucketManager OK.");
        return uploadManager;
    }
}
