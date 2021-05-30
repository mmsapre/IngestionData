package com.test.ingestion.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@ComponentScan("com.test.ingestion")
@Slf4j
public class RedisConfiguration extends CachingConfigurerSupport {

    @Value("${Redis.DB.Url}")
    private String url;

    @Value("${Redis.DB.Instance}")
    private Integer instance;

    @Value("${Redis.DB.Password}")
    private String password;


    @Value("${Redis.DB.TimeOut}")
    private Integer timeout;

    @Value("${Redis.Cache.TTL:50000}")
    private Integer cacheEviction;


    @Bean
    @Primary
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory factory=null;
            factory = new JedisConnectionFactory();
            final String[] splitedUrl = url.split(":");
            factory.setHostName(splitedUrl[0]);
            factory.setPort(Integer.parseInt(splitedUrl[1]));
            factory.setDatabase(instance);
            factory.setPassword(password);
            factory.setPoolConfig(jedisPoolConfig());
            factory.setUsePool(true);
            factory.setUseSsl(false);
            factory.setTimeout(timeout);
        return factory;
    }

    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(30);
        jedisPoolConfig.setMinIdle(5);
        return jedisPoolConfig;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        final RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return createCacheConfiguration();
    }

    private  RedisCacheConfiguration createCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(this.cacheEviction));
    }

    @Bean
    public CacheManager cacheManager(RedisCacheConfiguration redisCacheConfiguration,JedisConnectionFactory jedisConnectionFactory) {

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory);
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        return RedisCacheManager.builder().cacheDefaults(redisCacheConfiguration).cacheWriter(redisCacheWriter).build();
    }

    @Bean(name = "internalIdCache")
    public Map<String, String> internalIdCache() {

        Map<String, String> internalIdMap = new java.util.concurrent.ConcurrentHashMap<>();
        internalIdMap.put("INE096B01018", "BNK-096B01018");
        internalIdMap.put("INE733E01010", "BNK-733E01010");
        internalIdMap.put("INE002A01018", "BNK-002A01018");
        internalIdMap.put("INE306L01010", "BNK-306L01010");
        return internalIdMap;
    }

}
