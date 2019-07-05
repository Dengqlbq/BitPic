package com.deng.bitpic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @description: Redis
 * @author: Deng
 * @create: 2019-01-19
 */
@Repository
public class RedisRepository {

    @Autowired
    private StringRedisTemplate redis;

    public String get(String key) {
        return redis.opsForValue().get(key);
    }

    /**
     * 设置 key-value到redis
     * @param key key
     * @param value value
     * @param expire 过期时间
     * @param timeUnit 时间单位
     */
    public void set(String key, String value, Integer expire, TimeUnit timeUnit) {
        redis.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     * 删除redis中指定key
     * @param key key
     */
    public void delete(String key) {
        redis.delete(key);
    }
}
