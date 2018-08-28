package com.guoanshequ.dc.das.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * Created by daishuhua on 2017/7/12.
 */
@Configuration
@org.springframework.context.annotation.PropertySource("classpath:/config/redis.properties")
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private @Value("${spring.redis.cluster.password}") String redisPassword;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    RedisClusterConfiguration redisClusterConfiguration() {
        PropertySource propertySource = null;
        try {
            propertySource = new ResourcePropertySource("/config/redis.properties");
        } catch (IOException e) {
            logger.error("loading redis cluster config exception. message: {}, cause: {}.", e.getMessage(), e.getCause());
        }
        assert propertySource != null;
        return new RedisClusterConfiguration(propertySource);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig pooConfig = new JedisPoolConfig();
        JedisConnectionFactory factory = new JedisConnectionFactory(redisClusterConfiguration(), pooConfig);
        factory.setPassword(redisPassword);
        factory.setUsePool(true);
        return factory;
    }

    @Bean
    RedisTemplate< String, Object > redisTemplate() {
        final RedisTemplate< String, Object > template =  new RedisTemplate<>();
        template.setConnectionFactory( jedisConnectionFactory() );
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericToStringSerializer<>( Object.class ) );
        template.setValueSerializer( new GenericToStringSerializer<>( Object.class ) );
        return template;
    }
}
