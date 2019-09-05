package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.system.dao.dto.SystemDicDto;
import com.mtx.system.dao.model.SystemDic;
import com.mtx.system.dao.vo.Ztree;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class SystemDicFactory extends BaseFactory {
    @Override
    protected <V, E> V convertAttribute(E e, V v) {
        if(v instanceof Ztree){
            ((Ztree) v).setId(TypeConversionUtil.objectToInt(getGetMethod(e,"dicId")));
            ((Ztree) v).setPId(TypeConversionUtil.objectToInt(getGetMethod(e,"dicPid")));
            ((Ztree) v).setName(TypeConversionUtil.objectToString(getGetMethod(e,"dicName")));
        }

        return v;
    }


    public SystemDic convertParent(SystemDicDto systemDicDto) {
        SystemDic systemDic=new SystemDic();
        BeanUtils.copyProperties(systemDicDto,systemDic);
        systemDic.setDicPid(0);
        systemDic.setEditDate(new Date());
        systemDic.setEditUser(1);
        return systemDic;
    }
}
