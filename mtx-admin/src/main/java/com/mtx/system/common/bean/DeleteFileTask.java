package com.mtx.system.common.bean;

import com.mtx.system.common.file.UploadComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteFileTask {
    private UploadComponent uploadComponent = new UploadComponent();
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void deleteFile(final String... filePaths) {
        this.threadPoolTaskExecutor.execute(new DeleteFileThread(filePaths));
    }

    private class DeleteFileThread implements Runnable {
        String[] filePaths ;
        private DeleteFileThread(String[] filePaths) {
            super();
            this.filePaths =filePaths;
        }
        @Override
        public void run() {
            for (String filePath : filePaths) {
                if (StringUtils.isBlank(filePath)) {
                    continue;
                }
                uploadComponent.delete(filePath);
            }
        }
    }




}
