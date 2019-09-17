package com.mtx.system.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.FileUtil;
import com.mtx.common.util.base.RedisUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.AttachmentEnum;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.common.file.UploadComponent;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.mapper.SystemAttachMapper;
import com.mtx.system.dao.model.SystemAttach;
import com.mtx.system.dao.model.SystemAttachExample;
import com.mtx.system.dao.vo.SystemAttachVo;
import com.mtx.system.rpc.api.SystemAttachService;
import com.mtx.system.rpc.factory.SystemAttachFactory;
import com.mtx.system.rpc.mapper.SystemAttachExtMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* SystemAttachService实现
* Created by yu on 2018/12/27.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemAttachServiceImpl extends BaseServiceImpl<SystemAttachMapper, SystemAttach, SystemAttachExample> implements SystemAttachService  {
    private SystemAttachFactory systemAttachFactory = new SystemAttachFactory();
    private UploadComponent uploadComponent = new UploadComponent();
    @Autowired
    SystemAttachMapper systemAttachMapper;
    @Autowired
    SystemAttachExtMapper systemAttachExtMapper;
    @Autowired
    SystemAttachService systemAttachService;

    @Override
    public List<SystemAttachVo> list(Page<SystemAttach> page, SystemAttachDto systemAttachDto) {
        List<SystemAttachVo> list = systemAttachExtMapper.list(systemAttachDto,page,page.getOrderByField(),page.isAsc());
        List<SystemAttachVo> voList = systemAttachFactory.convertList(list,SystemAttachVo.class);
        return voList;
    }

    @Override
    public SystemAttachVo selectByIdWithLeft(int attachId) {
        SystemAttach systemAttach=systemAttachMapper.selectByPrimaryKey(attachId);
        SystemAttachVo systemAttachVo = systemAttachFactory.convertToVo(systemAttach,SystemAttachVo.class);
        return systemAttachVo;
    }

    @Override
    public int insertDto(SystemAttachDto systemAttachDto) {
        systemAttachDto =uploadComponent.upload(systemAttachDto);

        SystemAttach systemAttach = systemAttachFactory.convertDtoToDo(systemAttachDto,SystemAttach.class);
        return systemAttachMapper.insertSelective(systemAttach);
    }

    @Override
    public void deleteYunFile(String filePath) {
        Auth auth = Auth.create(GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_ID),
                GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_SECRET));
        String bucketName = GlobalProperties.me().getValueByCode(PropertiesEnum.BUCKET_NAME);
        Zone zone = Zone.autoZone();
        BucketManager bucketManager = new BucketManager(auth, new com.qiniu.storage.Configuration(zone));
        String destPath = SystemConstant.QINIU_YUN_FILE_PATH+filePath;//绝对路径
        try {
            bucketManager.delete(bucketName, destPath);
        } catch (QiniuException e) {
            log.error(e.getMessage(),e);
        }

    }

    @Override
    public int insertDtoCloud(SystemAttachDto systemAttachDto) {
        MultipartFile file =systemAttachDto.getFile();
        Preconditions.checkArgument(file.getSize() <= SystemConstant.FILE_MAX_SIZE, "上传文件不能大于5M");
        FileUtil.checkFileType(file.getContentType());//判断文件类型是否正确



        String fileName = file.getOriginalFilename();// 原文件名
        String newName = FileUtil.generateNewFileName(fileName);
        String filePath = FileUtil.generateFilePath(newName);
        String destPath = SystemConstant.QINIU_YUN_FILE_PATH+filePath;//绝对路径

        try {
            // 检查数据大小
            this.checkFileSize(file.getBytes());

            Auth auth = Auth.create(GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_ID),
                    GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_ACCESS_KEY_SECRET));
            String bucketName = GlobalProperties.me().getValueByCode(PropertiesEnum.BUCKET_NAME);
            Zone zone = Zone.autoZone();
            UploadManager uploadManager = new UploadManager(new com.qiniu.storage.Configuration(zone));
            Response response = uploadManager.put(file.getBytes(), destPath, auth.uploadToken(bucketName));
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            if (ToolUtil.isEmpty(putRet) || org.apache.commons.lang3.StringUtils.isEmpty(putRet.key)) {
                throw new BusinessException(ErrorCodeEnum.ATTACH10050001);
            }
            //String fileUrl = GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_IMAGE_DOMAIN)+"/"+destPath;


        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        systemAttachDto.setEditDate(new Date());
        systemAttachDto.setFileName(fileName);
        systemAttachDto.setFilePath(filePath);
        systemAttachDto.setFileSize(file.getSize());
        systemAttachDto.setSuffix(file.getContentType());
        systemAttachDto.setNewName(newName);
        SystemAttach systemAttach = systemAttachFactory.convertDtoToDo(systemAttachDto,SystemAttach.class);
        return systemAttachMapper.insertSelective(systemAttach);
    }

    private void checkFileSize(byte[] bytes) {
        long redisFileSize;
        Long fileMaxSize = 1024*1024*1024*10L;
        long fileSize = bytes.length;

        String redisFileSizeStr = RedisUtil.get(SystemConstant.SYSTEM_QIUNIU_FILESIZE);
        if(StringUtils.isEmpty(redisFileSizeStr)) {
            redisFileSizeStr = "0";
        }
        redisFileSize = Long.valueOf(redisFileSizeStr);
        if (fileSize + redisFileSize > fileMaxSize) {
            throw new BusinessException(ErrorCodeEnum.ATTACH10050002);
        }

        RedisUtil.set(SystemConstant.SYSTEM_QIUNIU_FILESIZE , String.valueOf(redisFileSize + fileSize), 60*24);

    }


    @Override
    public String[] selectForFilePaths(String ids) {
        List<String> strings=new ArrayList<>();
        if (StringUtils.isBlank(ids)) {
            return new String[0];
        }
        String[] idArray = ids.split("-");
        for (String idStr : idArray) {
            if (StringUtils.isBlank(idStr)) {
                continue;
            }
            Integer id = Integer.parseInt(idStr);
            SystemAttach systemAttach=systemAttachMapper.selectByPrimaryKey(id);
            strings.add(systemAttach.getFilePath());
        }
        return TypeConversionUtil.listToStrings(strings);
    }

    @Override
    public void insertDtoQqHead(SystemUserDto systemUserDto) {
        SystemAttachDto systemAttachDto = new SystemAttachDto();
        //判断是什么业务
        MultipartFile file = FileUtil.createFileItem(systemUserDto.getAvatar());
        systemAttachDto.setFile(file);
        systemAttachDto.setBizType(AttachmentEnum.HEAD_ATTACHMENT.name());
        systemAttachDto.setBizId(systemUserDto.getUserId());
        systemAttachDto.setEditUser(systemUserDto.getUserId());
        systemAttachDto.setAddressType(TypeConversionUtil.objectToByte(GlobalProperties.me().getValueByCode(PropertiesEnum.FILE_ADDRESS_TYPE)));
        if(systemAttachDto.getAddressType()==1){
            systemAttachService.insertDto(systemAttachDto);
        }else {
            systemAttachService.insertDtoCloud(systemAttachDto);
        }
    }
}