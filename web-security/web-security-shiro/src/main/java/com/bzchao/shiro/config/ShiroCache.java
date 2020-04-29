package com.bzchao.shiro.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.exception.PrincipalIdNullException;
import org.crazycake.shiro.exception.PrincipalInstanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class ShiroCache<K, V> implements Cache<K, V> {
    @Autowired
    private RedisTemplate<Object, V> redisTemplate;
    private String prefix = "webcore";

    @Override
    public V get(K k) throws CacheException {
        String serKey = getSerKey(k);
        return redisTemplate.opsForValue().get(serKey);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String serKey = getSerKey(k);
        redisTemplate.opsForValue().set(serKey, v, 60 * 60, TimeUnit.SECONDS);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        String serKey = getSerKey(k);
        V v = redisTemplate.opsForValue().get(serKey);
        redisTemplate.expire(serKey, -1, TimeUnit.SECONDS);
        return v;
    }

    @Override
    public void clear() throws CacheException {

    }

    private String getSerKey(K key) {
        if (key instanceof PrincipalCollection) {
            String keyString = getRedisKeyFromPrincipalIdField((PrincipalCollection) key, "username");
            return prefix + ":" + keyString;
        }
        return prefix + ":" + key.toString();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    private String getRedisKeyFromPrincipalIdField(PrincipalCollection key, String principalIdFieldName) {
        String redisKey;
        Object principalObject = key.getPrimaryPrincipal();
        Method pincipalIdGetter = null;
        Method[] methods = principalObject.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (RedisCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME.equals(principalIdFieldName)
                    && ("getAuthCacheKey".equals(m.getName()) || "getId".equals(m.getName()))) {
                pincipalIdGetter = m;
                break;
            }
            if (m.getName().equals("get" + principalIdFieldName.substring(0, 1).toUpperCase() + principalIdFieldName.substring(1))) {
                pincipalIdGetter = m;
                break;
            }
        }
        if (pincipalIdGetter == null) {
            throw new PrincipalInstanceException(principalObject.getClass(), principalIdFieldName);
        }

        try {
            Object idObj = pincipalIdGetter.invoke(principalObject);
            if (idObj == null) {
                throw new PrincipalIdNullException(principalObject.getClass(), principalIdFieldName);
            }
            redisKey = idObj.toString();
        } catch (IllegalAccessException e) {
            throw new PrincipalInstanceException(principalObject.getClass(), principalIdFieldName, e);
        } catch (InvocationTargetException e) {
            throw new PrincipalInstanceException(principalObject.getClass(), principalIdFieldName, e);
        }

        return redisKey;
    }

}