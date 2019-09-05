package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.common.util.model.SelectVo;
import com.mtx.common.util.secret.MD5Util;
import com.mtx.system.common.bean.DictCacheKit;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.DictEnum;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.model.SystemOrganization;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.dao.vo.SystemUserVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemUserFactory extends BaseFactory{


    @Override
    protected <V, E> V convertAttribute(E e, V v) {


        return v;
    }

    //递归获取父子
    public List<Integer> findAllParent(int organizationId, List<SystemOrganization> allList) {
        List<Integer> result = new ArrayList<>();
        for(SystemOrganization systemOrganization:allList){
            if(systemOrganization.getOrganizationId()==organizationId){
                result.add(systemOrganization.getOrganizationId());
                List<Integer> subList=findAllParent(systemOrganization.getOrganizationPid(),allList);
                result.addAll(subList);
            }
        }
        return result;
    }

    public List<Integer> findAllChild(int organizationId, List<SystemOrganization> allList) {
        List<Integer> result = new ArrayList<>();
        for(SystemOrganization systemOrganization:allList){
            if(systemOrganization.getOrganizationPid()==organizationId){
                result.add(systemOrganization.getOrganizationId());
                List<Integer> subList=findAllChild(systemOrganization.getOrganizationId(),allList);
                result.addAll(subList);
            }
        }
        result.add(organizationId);
        return result;
    }

    public SystemUser convertDtoToDo(SystemUserDto systemUserDto, Class<SystemUser> systemUserClass) {
        SystemUser systemUser = convertModel(systemUserDto,systemUserClass);
        systemUser.setEditDate(new Date());
        systemUser.setEditUser(1);
        systemUser.setSalt(ToolUtil.getUuid());
        systemUser.setPassword(StringUtil.toSecretString(systemUser.getSalt()));
        systemUser.setExtendMap(TypeConversionUtil.objectToJsonToBytes(systemUserDto.getExtProps()));
        systemUser.setDeleted((byte)0);

        return systemUser;

    }


    public SystemUser convertDtoToDoEdit(SystemUserDto systemUserDto, Class<SystemUser> systemUserClass) {
        SystemUser systemUser = convertModel(systemUserDto,systemUserClass);
        systemUser.setEditDate(new Date());
        systemUser.setEditUser(1);
        systemUser.setExtendMap(TypeConversionUtil.objectToJsonToBytes(systemUserDto.getExtProps()));

        return systemUser;
    }

    public SystemUserVo convertToVo(SystemUser systemUser, Class<SystemUserVo> systemUserVoClass) {
        SystemUserVo systemUserVo =convertModel(systemUser,systemUserVoClass);
        systemUserVo.setExtProps(TypeConversionUtil.bytesToJsonToObject(systemUserVo.getExtendMap()));
        systemUserVo.setSexName(DictCacheKit.me().getDictNameByCode(DictEnum.SEX,""+systemUserVo.getSex()));
        systemUserVo.setUserStateName(DictCacheKit.me().getDictNameByCode(DictEnum.USER_STATE,""+systemUserVo.getUserState()));
        return systemUserVo;
    }

    public String convertDetail(List<SelectVo> listSelect) {
        StringBuffer sb =new StringBuffer();
        for (SelectVo selectVo:listSelect){
            sb.append(selectVo.getName()+" ");
        }
        return sb.toString();
    }
}
