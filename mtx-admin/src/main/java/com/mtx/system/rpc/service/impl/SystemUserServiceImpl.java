package com.mtx.system.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.annotation.BaseService;
import com.mtx.common.base.BaseServiceImpl;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.AttachmentEnum;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.common.exception.ErrorCodeEnum;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.mapper.SystemUserMapper;
import com.mtx.system.dao.model.*;
import com.mtx.system.dao.vo.SystemUserVo;
import com.mtx.system.rpc.api.*;
import com.mtx.system.rpc.factory.SystemUserFactory;
import com.mtx.system.rpc.mapper.SystemUserExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* SystemUserService实现
* Created by yu on 2018/12/9.
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUserMapper, SystemUser, SystemUserExample> implements SystemUserService {
    private SystemUserFactory systemUserFactory = new SystemUserFactory();
    @Autowired
    SystemUserMapper systemUserMapper;
    @Autowired
    SystemUserExtMapper systemUserExtMapper;
    @Autowired
    SystemOrganizationService systemOrganizationService;
    @Autowired
    SystemUserOrganizationService systemUserOrganizationService;
    @Autowired
    SystemUserRoleService systemUserRoleService;
    @Autowired
    SystemAttachService systemAttachService;

    @Override
    public List<SystemUserVo> list(Page<SystemUserVo> page, int organizationId, String search) {
        List<SystemOrganization> allList = systemOrganizationService.selectByExample(new SystemOrganizationExample());
        List<Integer> organizationList = systemUserFactory.findAllChild(organizationId,allList);
        List<SystemUserVo> list = systemUserExtMapper.list(organizationList,search,page,page.getOrderByField(),page.isAsc());
        return list;
    }

    @Override
    public SystemUserVo selectByIdWithLeft(int userId) {
        SystemUser systemUser=systemUserMapper.selectByPrimaryKey(userId);
        SystemAttachExample systemAttachExample=new SystemAttachExample();
        SystemAttachExample.Criteria criteria = systemAttachExample.createCriteria();
        criteria.andBizTypeEqualTo(AttachmentEnum.HEAD_ATTACHMENT.name())
        .andBizIdEqualTo(userId);
        systemAttachExample.setOrderByClause("attach_id desc");
        List<SystemAttach> list =systemAttachService.selectByExample(systemAttachExample);

        SystemUserVo systemUserVo = systemUserFactory.convertToVo(systemUser,SystemUserVo.class);
        if(ToolUtil.isNotEmpty(list)){
            SystemAttach systemAttach =list.get(0);
            systemUserVo.setAvatar(systemAttach.getFilePath());
            systemUserVo.setAddressType(systemAttach.getAddressType());
            systemUserVo.setYunPath("http://"+ GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_IMAGE_DOMAIN)+"/mtx/upload/"+systemAttach.getFilePath());
        }
        return systemUserVo;
    }

    @Override
    public Integer insertDto(SystemUserDto systemUserDto) {
        //校验   手机号，邮箱不可以重复
        SystemUserExample systemUserExample=new SystemUserExample();
        SystemUserExample.Criteria criteria = systemUserExample.createCriteria();
        criteria.andPhoneEqualTo(systemUserDto.getPhone());
        criteria.andUserStateNotEqualTo((byte) 3);
        List<SystemUser> list =systemUserMapper.selectByExample(systemUserExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.USER10030001);
        }
        if(ToolUtil.isNotEmpty(systemUserDto.getEmail())){
            systemUserExample=new SystemUserExample();
            criteria = systemUserExample.createCriteria();
            criteria.andEmailEqualTo(systemUserDto.getEmail());
            criteria.andUserStateNotEqualTo((byte) 3);
            list =systemUserMapper.selectByExample(systemUserExample);
            if(!ToolUtil.isEmpty(list)){
                throw new BusinessException(ErrorCodeEnum.USER10030002);
            }
        }


        SystemUser systemUser = systemUserFactory.convertDtoToDo(systemUserDto,SystemUser.class);
        Integer count = systemUserMapper.insertSelective(systemUser);
        //插入组织信息  先删后加
        SystemUserOrganizationExample systemUserOrganizationExample = new SystemUserOrganizationExample();
        systemUserOrganizationExample.createCriteria()
                .andUserIdEqualTo(systemUser.getUserId());
        systemUserOrganizationService.deleteByExample(systemUserOrganizationExample);

        if (null != systemUserDto.getOrganizationId()) {
            for (String organizationId : systemUserDto.getOrganizationId()) {
                if (StringUtils.isBlank(organizationId)) {
                    continue;
                }
                SystemUserOrganization systemUserOrganization = new SystemUserOrganization();
                systemUserOrganization.setUserId(systemUser.getUserId());
                systemUserOrganization.setOrganizationId(NumberUtils.toInt(organizationId));
                count = systemUserOrganizationService.insertSelective(systemUserOrganization);
            }
        }
        //插入角色信息  先删后加
        SystemUserRoleExample systemUserRoleExample = new SystemUserRoleExample();
        systemUserRoleExample.createCriteria()
                .andUserIdEqualTo(systemUser.getUserId());
        systemUserRoleService.deleteByExample(systemUserRoleExample);

        if (null != systemUserDto.getRoleId()) {
            for (String roleId : systemUserDto.getRoleId()) {
                if (StringUtils.isBlank(roleId)) {
                    continue;
                }
                SystemUserRole systemUserRole = new SystemUserRole();
                systemUserRole.setUserId(systemUser.getUserId());
                systemUserRole.setRoleId(NumberUtils.toInt(roleId));
                count = systemUserRoleService.insertSelective(systemUserRole);
            }
        }

        return count;
    }

    @Override
    public Integer updateDto(SystemUserDto systemUserDto) {
        SystemUserExample systemUserExample=new SystemUserExample();
        SystemUserExample.Criteria criteria = systemUserExample.createCriteria();
        criteria.andPhoneEqualTo(systemUserDto.getPhone());
        criteria.andUserIdNotEqualTo(systemUserDto.getUserId());
        List<SystemUser> list =systemUserMapper.selectByExample(systemUserExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.USER10030001);
        }
        systemUserExample=new SystemUserExample();
        criteria = systemUserExample.createCriteria();
        criteria.andEmailEqualTo(systemUserDto.getEmail());
        criteria.andUserIdNotEqualTo(systemUserDto.getUserId());
        list =systemUserMapper.selectByExample(systemUserExample);
        if(!ToolUtil.isEmpty(list)){
            throw new BusinessException(ErrorCodeEnum.USER10030002);
        }

        SystemUser systemUser = systemUserFactory.convertDtoToDoEdit(systemUserDto,SystemUser.class);
        Integer count = systemUserMapper.updateByPrimaryKeySelective(systemUser);
        //插入组织信息  先删后加
        SystemUserOrganizationExample systemUserOrganizationExample = new SystemUserOrganizationExample();
        systemUserOrganizationExample.createCriteria()
                .andUserIdEqualTo(systemUser.getUserId());
        systemUserOrganizationService.deleteByExample(systemUserOrganizationExample);

        if (null != systemUserDto.getOrganizationId()) {
            for (String organizationId : systemUserDto.getOrganizationId()) {
                if (StringUtils.isBlank(organizationId)) {
                    continue;
                }
                SystemUserOrganization systemUserOrganization = new SystemUserOrganization();
                systemUserOrganization.setUserId(systemUser.getUserId());
                systemUserOrganization.setOrganizationId(NumberUtils.toInt(organizationId));
                count = systemUserOrganizationService.insertSelective(systemUserOrganization);
            }
        }
        //插入角色信息  先删后加
        SystemUserRoleExample systemUserRoleExample = new SystemUserRoleExample();
        systemUserRoleExample.createCriteria()
                .andUserIdEqualTo(systemUser.getUserId());
        systemUserRoleService.deleteByExample(systemUserRoleExample);

        if (null != systemUserDto.getRoleId()) {
            for (String roleId : systemUserDto.getRoleId()) {
                if (StringUtils.isBlank(roleId)) {
                    continue;
                }
                SystemUserRole systemUserRole = new SystemUserRole();
                systemUserRole.setUserId(systemUser.getUserId());
                systemUserRole.setRoleId(NumberUtils.toInt(roleId));
                count = systemUserRoleService.insertSelective(systemUserRole);
            }
        }
        return count;
    }

    @Override
    public int deleteByPrimaryKeyFaker(int id) {
        SystemUser systemUser = new SystemUser();
        systemUser.setDeleted((byte) 1);
        systemUser.setUserId(id);
        return systemUserMapper.updateByPrimaryKeySelective(systemUser);
    }

    @Override
    public int deleteByPrimaryKeysFaker(String ids) {
        List<Object> list = TypeConversionUtil.stringsToList(ids);
        return systemUserExtMapper.deleteByPrimaryKeysFaker(list);
    }

    @Override
    public SystemUser selectByQqOpenId(String openid) {
        SystemUserExample systemUserExample=new SystemUserExample();
        SystemUserExample.Criteria criteria = systemUserExample.createCriteria();
        criteria.andQqOpenIdEqualTo(openid);
        List<SystemUser> list = systemUserMapper.selectByExample(systemUserExample);
        if(ToolUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updateOpenId(SystemUser systemUser) {
        SystemUser user = new SystemUser();
        user.setQqOpenId(systemUser.getQqOpenId());
        user.setUserId(systemUser.getUserId());
        user.setNickName(systemUser.getNickName());
        systemUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateByUser(SystemUser systemUser) {
        SystemUser user =new SystemUser();
        user.setUserId(systemUser.getUserId());
        user.setLastLogin(new Date());
        user.setLastIp(systemUser.getLastIp());
        systemUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateDtoOnly(SystemUserDto systemUserDto) {
        SystemUser systemUser = systemUserFactory.convertDtoToDoEdit(systemUserDto,SystemUser.class);
        return systemUserMapper.updateByPrimaryKeySelective(systemUser);
    }

    @Override
    public void activeEmail(SystemUserDto systemUserDto) {
        boolean active = false;
        List<SystemUser> list = systemUserMapper.selectByExampleWithBLOBs(new SystemUserExample());
        List<SystemUserVo> voList = systemUserFactory.convertList(list,SystemUserVo.class);
        for(SystemUserVo systemUserVo:voList){
            systemUserVo.setExtProps(TypeConversionUtil.bytesToJsonToObject(systemUserVo.getExtendMap()));
            if(systemUserDto.getEmailToken().equals(systemUserVo.getEmailToken())){
                SystemUser user =new SystemUser();
                user.setUserId(systemUserVo.getUserId());
                user.setEmail(systemUserDto.getEmail());
                systemUserMapper.updateByPrimaryKeySelective(user);
                active = true;
                return;
            }
        }
        if(!active){
            throw new BusinessException(ErrorCodeEnum.USER10030003);
        }

    }
}

