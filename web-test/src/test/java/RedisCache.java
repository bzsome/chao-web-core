import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache<K, V> implements Cache<K, V> {

    @Resource
    RedisTemplate redisTemplate;

    private final String CACHE_PREFIX = "redis-cache";

    private byte[] getKey(K k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public V get(K key) throws CacheException {
        System.out.println("从redis中取数据");
        return (V) redisTemplate.opsForValue().get(getKey(key));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(k, v, 600, TimeUnit.SECONDS);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        Object o = redisTemplate.opsForValue().get(k);
        redisTemplate.delete(k);
        return (V) o;
    }

    @Override
    public void clear() throws CacheException {

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
}