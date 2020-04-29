package com.bzchao.shiro.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ShiroCacheManager implements CacheManager {
    @Autowired
    private ShiroCache shiroCache;
    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return shiroCache;
    }

}
