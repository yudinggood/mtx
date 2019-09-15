package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.system.common.bean.GlobalProperties;
import com.mtx.system.common.enums.AttachmentEnum;
import com.mtx.system.common.enums.PropertiesEnum;
import com.mtx.system.dao.dto.SystemAttachDto;
import com.mtx.system.dao.model.SystemAttach;
import com.mtx.system.dao.vo.SystemAttachVo;

public class SystemAttachFactory extends BaseFactory {
    @Override
    protected <V, E> V convertAttribute(E e, V v) {
        if(v instanceof SystemAttachVo){
            ((SystemAttachVo) v).setBizTypeName(AttachmentEnum.codeOf((String) getGetMethod(e,"bizType")).getName());
            ((SystemAttachVo) v).setFileSizeName(StringUtil.convertFileSize(TypeConversionUtil.objectToLong(getGetMethod(e,"fileSize"))));
            ((SystemAttachVo) v).setYunPath("http://"+GlobalProperties.me().getValueByCode(PropertiesEnum.QINIU_IMAGE_DOMAIN)+"/mtx/upload/"+getGetMethod(e,"filePath"));

        }
        return v;
    }


    public SystemAttachVo convertToVo(SystemAttach systemAttach, Class<SystemAttachVo> systemAttachVoClass) {
        SystemAttachVo systemAttachVo =convertModel(systemAttach,systemAttachVoClass);
        return systemAttachVo;
    }

    public SystemAttach convertDtoToDo(SystemAttachDto systemAttachDto, Class<SystemAttach> systemAttachClass) {
        SystemAttach systemAttach = convertModel(systemAttachDto,systemAttachClass);
        return systemAttach;
    }
}
