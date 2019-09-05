package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.common.util.model.SelectVo;
import com.mtx.system.dao.dto.SystemRoleDto;
import com.mtx.system.dao.model.SystemRole;

import java.util.Date;

public class SystemRoleFactory extends BaseFactory{

    @Override
    protected <V, E> V convertAttribute(E e, V v) {
        if(v instanceof SelectVo){
            ((SelectVo) v).setCode(TypeConversionUtil.objectToString(getGetMethod(e,"roleId")));
            ((SelectVo) v).setName(TypeConversionUtil.objectToString(getGetMethod(e,"roleName")));
        }

        return v;
    }


    public SystemRole convertDtoToDo(SystemRoleDto systemRoleDto, Class<SystemRole> systemRoleClass) {
        SystemRole systemRole = convertModel(systemRoleDto,systemRoleClass);
        systemRole.setEditDate(new Date());
        systemRole.setEditUser(1);
        return systemRole;
    }
}
