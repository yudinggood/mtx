package com.mtx.common.util.cache;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * Ehcache缓存工厂
 */
@Slf4j
public class EhcacheFactory extends BaseCacheFactory{
    private static CacheManager cacheManager;
    private static volatile Object locker = new Object();
    //被volatile修饰的变量能够保证每个线程能够获取该变量的最新值，从而避免出现数据脏读的现象

    private static CacheManager getCacheManager() {
        if (cacheManager == null) {
            synchronized (EhcacheFactory.class) {
                if (cacheManager == null) {
                    cacheManager = CacheManager.create();
                }
            }
        }
        return cacheManager;
    }

    static Cache getOrAddCache(String cacheName) {
        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            synchronized(locker) {
                cache = cacheManager.getCache(cacheName);
                if (cache == null) {
                    log.warn("无法找到缓存 [" + cacheName + "]的配置, 使用默认配置.");
                    cacheManager.addCacheIfAbsent(cacheName);
                    cache = cacheManager.getCache(cacheName);
                    log.debug("缓存 [" + cacheName + "] 启动.");
                }
            }
        }
        return cache;
    }

    @Override
    public void put(String cacheName, Object key, Object value) {
        getOrAddCache(cacheName).put(new Element(key, value));
    }

    @Override
    public <T> T get(String cacheName, Object key) {
        Element element = getOrAddCache(cacheName).get(key);
        return element != null ? (T)element.getObjectValue() : null;
    }

    @Override
    public List getKeys(String cacheName) {
        return getOrAddCache(cacheName).getKeys();
    }

    @Override
    public void remove(String cacheName, Object key) {
        getOrAddCache(cacheName).remove(key);
    }

    @Override
    public void removeAll(String cacheName) {
        getOrAddCache(cacheName).removeAll();
    }
}
