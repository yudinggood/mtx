package com.mtx.common.util.cache;
/**
 * 缓存工厂基类
 */
public abstract class BaseCacheFactory implements ICache{
    @Override
    public <T> T get(String cacheName, Object key, ILoader iLoader) {
        Object data = get(cacheName, key);//抽象类里使用了 抽象方法，实际上方法会在子类的 实现方法中执行
        if (data == null) {
            data = iLoader.load();//调用了其它接口的 抽象方法，可以在子类中重写实现
            put(cacheName, key, data);
        }
        return (T) data;
    }

    @Override
    public <T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass) {
        Object data = get(cacheName, key);
        if (data == null) {
            try {
                ILoader dataLoader = iLoaderClass.newInstance();
                data = dataLoader.load();
                put(cacheName, key, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (T) data;
    }
}
