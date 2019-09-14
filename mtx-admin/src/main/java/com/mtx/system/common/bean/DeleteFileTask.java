package com.mtx.system.common.bean;

import com.mtx.common.util.base.FileUtil;
import com.mtx.common.util.base.PropertiesFileUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.system.common.file.UploadComponent;
import com.mtx.system.dao.vo.SystemAttachVo;
import com.mtx.system.rpc.api.SystemAttachService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class DeleteFileTask {
    private UploadComponent uploadComponent = new UploadComponent();
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void deleteFileByPaths(final String... filePaths){
            // 使用线程池多线程处理
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (String filePath : filePaths) {
                            if (StringUtils.isBlank(filePath)) {
                                continue;
                            }
                            uploadComponent.delete(filePath);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(),e);
                    }
                }
            });

    }

}
