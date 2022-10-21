package com.dezhentech.framework.cache.service.impl;

import com.dezhentech.framework.cache.service.CacheService;
import com.dezhentech.framework.redis.dao.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: Redis缓存Service
 * @title: com.dezhentech.framework.cache.redis.util.RedisUtil
 * @author: yfcui@dezhentech.com
 * @create: 2022/10/20 05:07:16
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.cache", name = "type", havingValue = "redis")
public class RedisCacheServiceImpl implements CacheService {
    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void setExpire(byte[] key, byte[] value, long time) {
        redisRepository.setExpire(key, value, time);
    }

    @Override
    public void setExpire(String key, Object value, long time, TimeUnit timeUnit) {
        redisRepository.setExpire(key, value, time, timeUnit);
    }

    @Override
    public long getExpire(String key, TimeUnit timeUnit) {
        return redisRepository.getExpire(key, timeUnit);
    }

    @Override
    public void setExpire(String key, Object value, long time) {
        redisRepository.setExpire(key, value, time);
    }

    @Override
    public void setExpire(String key, Object value, long time, TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {
        redisRepository.setExpire(key, value, time, timeUnit, valueSerializer);
    }

    @Override
    public void setExpire(String[] keys, Object[] values, long time) {
        redisRepository.setExpire(keys, values, time);
    }

    @Override
    public void set(String[] keys, Object[] values) {
        redisRepository.set(keys, values);
    }

    @Override
    public void set(String key, Object value) {
        redisRepository.set(key, value);
    }

    @Override
    public Set<String> keys(String keyPatten) {
        return redisRepository.keys(keyPatten);
    }

    @Override
    public byte[] get(byte[] key) {
        return redisRepository.get(key);
    }

    @Override
    public Object get(String key) {
        return redisRepository.get(key);
    }

    @Override
    public Object get(String key, RedisSerializer<Object> valueSerializer) {
        return redisRepository.get(key, valueSerializer);
    }

    @Override
    public HashOperations<String, String, Object> opsForHash() {
        return redisRepository.opsForHash();
    }

    @Override
    public void putHashValue(String key, String hashKey, Object hashValue) {
        redisRepository.putHashValue(key, hashKey, hashValue);
    }

    @Override
    public Object getHashValues(String key, String hashKey) {
        return redisRepository.getHashValues(key, hashKey);
    }

    @Override
    public void delHashValues(String key, Object... hashKeys) {
        redisRepository.delHashValues(key, hashKeys);
    }

    @Override
    public Map<String, Object> getHashValue(String key) {
        return redisRepository.getHashValue(key);
    }

    @Override
    public void putHashValues(String key, Map<String, Object> map) {
        redisRepository.putHashValues(key, map);
    }

    @Override
    public long dbSize() {
        return 0;
    }

    @Override
    public String flushDB() {
        return null;
    }

    @Override
    public boolean exists(String key) {
        return false;
    }

    @Override
    public boolean del(String... keys) {
        return false;
    }

    @Override
    public long incr(String key) {
        return 0;
    }

    @Override
    public ListOperations<String, Object> opsForList() {
        return null;
    }

    @Override
    public Long leftPush(String key, Object value) {
        return null;
    }

    @Override
    public Object leftPop(String key) {
        return null;
    }

    @Override
    public Long in(String key, Object value) {
        return null;
    }

    @Override
    public Object rightPop(String key) {
        return null;
    }

    @Override
    public Long length(String key) {
        return null;
    }

    @Override
    public void remove(String key, long i, Object value) {

    }

    @Override
    public void set(String key, long index, Object value) {

    }

    @Override
    public List<Object> getList(String key, int start, int end) {
        return null;
    }

    @Override
    public List<Object> getList(String key, int start, int end, RedisSerializer<Object> valueSerializer) {
        return null;
    }

    @Override
    public Long leftPushAll(String key, List<String> list) {
        return null;
    }

    @Override
    public void insert(String key, long index, Object value) {

    }

    @Override
    public byte[] rawKey(Object key) {
        return new byte[0];
    }

    @Override
    public byte[] rawValue(Object value, RedisSerializer valueSerializer) {
        return new byte[0];
    }

    @Override
    public Object deserializeValue(byte[] value, RedisSerializer<Object> valueSerializer) {
        return null;
    }
}
