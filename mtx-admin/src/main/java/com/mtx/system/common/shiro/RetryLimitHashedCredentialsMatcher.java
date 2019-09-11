package com.mtx.system.common.shiro;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.PropertiesFileUtil;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>限制登录次数<=5
 */
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //如果是免密登录直接返回true
        String systemType = PropertiesFileUtil.getInstance().get(SystemConstant.UPMS_TYPE);
        if (SystemConstant.CLIENT.equals(systemType)) {
            return true;
        }
        EasyTypeToken tk = (EasyTypeToken) token;
        if(!tk.isNormal()){
            return true;
        }


        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            log.warn("username: " + username + " 限制登录次数<=5");
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);//密码校验
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }

}
