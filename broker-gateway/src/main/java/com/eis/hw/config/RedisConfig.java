package com.eis.hw.config;
//

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//
///**
// * Created by kaclarpt on 2019/5/20
// */
@Configuration
public class RedisConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean("conn")
    RedisConnection getConn(){
        return redisConnectionFactory.getConnection();
    }

    @Bean("forObject")
    RedisTemplate createRedisTemplate(RedisConnectionFactory factory
    ) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        template.setConnectionFactory(factory);
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(redisSerializer);
        return template;
    }

    @Bean("forObject_byte")
    RedisTemplate createRedisTemplate2(RedisConnectionFactory factory
    ) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        template.setConnectionFactory(factory);
        template.setKeySerializer(stringSerializer);
        template.setEnableDefaultSerializer(false);
        return template;
    }
}