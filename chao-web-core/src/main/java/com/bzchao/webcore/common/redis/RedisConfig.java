package com.bzchao.webcore.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 自己定义一个 redisTemplate，并设置相关参数
     */
    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 为了我们自己开发方便，一般直接用 <String, Object>
        RedisTemplate<String, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Json 序列化配置
        // 使用 Jackson2JsonRedisSerializer 替换默认的 JdkSerializationRedisSerializer 来序列化和反序列化 redis的value值
        Jackson2JsonRedisSerializer jackson2Serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        jackson2Serializer.setObjectMapper(objectMapper);
        template.setDefaultSerializer(jackson2Serializer);

        // String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);       // hash的key也采用String的序列化方式

        template.setValueSerializer(jackson2Serializer);   // value序列化方式采用jackson
        // template.setValueSerializer(stringRedisSerializer);         // value 采用 String 的方式
        template.setHashValueSerializer(jackson2Serializer);    // hash的value序列化方式采用jackson

        template.afterPropertiesSet();

        return template;
    }
}
