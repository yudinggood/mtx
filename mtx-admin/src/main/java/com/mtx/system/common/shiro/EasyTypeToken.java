package com.mtx.system.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class EasyTypeToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -2564928913725078138L;
    private boolean normal;

    public EasyTypeToken() {
        super();
    }

    /**免密登录*/
    public EasyTypeToken(String username) {
        super(username, "", false, null);
        this.normal = false;
    }
    /**账号密码登录*/
    public EasyTypeToken(String username, String password) {
        super(username, password, null);
        this.normal = true;
    }

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }
}
