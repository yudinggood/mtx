package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemOrgDto;
import com.mtx.system.dao.model.SystemOrganization;
import com.mtx.system.dao.vo.Ztree;

import java.util.Date;

public class SystemOrgFactory extends BaseFactory{

    @Override
    protected <V, E> V convertAttribute(E e, V v) {
        if(v instanceof Ztree){
            ((Ztree) v).setId(TypeConversionUtil.objectToInt(getGetMethod(e,"organizationId")));
            ((Ztree) v).setPId(TypeConversionUtil.objectToInt(getGetMethod(e,"organizationPid")));
            ((Ztree) v).setName(TypeConversionUtil.objectToString(getGetMethod(e,"organizationName")));
        }
        if(v instanceof SelectVo){
            ((SelectVo) v).setCode(TypeConversionUtil.objectToString(getGetMethod(e,"organizationId")));
            ((SelectVo) v).setName(TypeConversionUtil.objectToString(getGetMethod(e,"organizationName")));
        }

        return v;
    }


    public SystemOrganization convertDtoToDo(SystemOrgDto systemOrgDto, Class<SystemOrganization> systemOrganizationClass) {
        SystemOrganization systemOrganization = convertModel(systemOrgDto,systemOrganizationClass);
        systemOrganization.setEditDate(new Date());
        systemOrganization.setEditUser(1);
        return systemOrganization;
    }
}
