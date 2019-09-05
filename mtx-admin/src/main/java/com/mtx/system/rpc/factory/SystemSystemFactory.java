package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.common.util.model.SelectVo;

public class SystemSystemFactory extends BaseFactory{

    @Override
    protected <V, E> V convertAttribute(E e, V v) {
        if(v instanceof SelectVo){
            ((SelectVo) v).setCode(TypeConversionUtil.objectToString(getGetMethod(e,"systemId")));
            ((SelectVo) v).setName(TypeConversionUtil.objectToString(getGetMethod(e,"name")));
        }

        return v;
    }






}
