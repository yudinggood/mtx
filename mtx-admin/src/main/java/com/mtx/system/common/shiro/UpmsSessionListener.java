package com.mtx.system.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会话监听
 */
@Slf4j
public class UpmsSessionListener implements SessionListener {


    @Override
    public void onStart(Session session) {
        log.debug("会话创建：" + session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.debug("会话停止：" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        log.debug("会话过期：" + session.getId());


    }

}
