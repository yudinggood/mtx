package com.mtx.system.dao.vo;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.PropertiesFileUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * QQ的appid
     */
    private String qqAppId;
    /**
     * QQ的授权路径
     */
    private String qqAuthPath;
    /**
     * QQ的授权密钥
     */
    private String qqClientSecret;

    public PageInfo() {
        this.qqAppId = SystemConstant.QQAPPID;
        StringBuffer ssoServerUrl = new StringBuffer(PropertiesFileUtil.getInstance().get("upms.sso.server.url"));
        this.qqAuthPath = ssoServerUrl+ SystemConstant.QQAUTHPATH;
        this.qqClientSecret  = SystemConstant.QQCLIENT_SECRET;
    }



}
