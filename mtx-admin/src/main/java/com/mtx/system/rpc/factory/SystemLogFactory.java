package com.mtx.system.rpc.factory;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.system.common.exception.BusinessException;
import com.mtx.system.dao.model.SystemError;

import java.util.Date;

public class SystemLogFactory extends BaseFactory {
    @Override
    protected <V, E> V convertAttribute(E e, V v) {

        return v;
    }


    public static SystemError createErrorLog(Integer userId,Exception exception) {
        String msg = ToolUtil.getExceptionMsg(exception);
        int type = 2;
        if(exception instanceof BusinessException){
            type=3;
        }
        StackTraceElement stackTraceElement= exception.getStackTrace()[0];

        SystemError systemError = new SystemError();
        systemError.setEditUser(userId);
        systemError.setMessage(msg);
        systemError.setEditDate(new Date());
        systemError.setErrorType((byte)type);
        systemError.setCode(exception.getClass().getName()+ SystemConstant.EMPTY_STRING+exception.getMessage());
        systemError.setMethod(stackTraceElement.getMethodName()+"("+stackTraceElement.getLineNumber()+"行)");
        systemError.setClassName(stackTraceElement.getClassName());
        return systemError;
    }

    public static SystemError createErrorLog(Integer userId) {
        SystemError systemError = new SystemError();
        systemError.setEditUser(userId);
        systemError.setEditDate(new Date());
        systemError.setErrorType((byte)1);
        systemError.setCode("登录成功");
        return systemError;
    }
}
