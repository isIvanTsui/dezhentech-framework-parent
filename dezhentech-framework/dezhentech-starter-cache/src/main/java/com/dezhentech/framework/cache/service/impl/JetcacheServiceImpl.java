package com.dezhentech.framework.cache.service.impl;

import com.dezhentech.framework.cache.service.CacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: jetcache缓存Service
 * @title: com.dezhentech.framework.cache.service.impl.JetcacheServiceImpl
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 09:29:58
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.cache", name = "type", havingValue = "jetcache")
public class JetcacheServiceImpl implements CacheService {
    @Override
    public void setExpire(byte[] key, byte[] value, long time) {

    }

    @Override
    public void setExpire(String key, Object value, long time, TimeUnit timeUnit) {

    }

    @Override
    public long getExpire(String key, TimeUnit timeUnit) {
        return 0;
    }

    @Override
    public void setExpire(String key, Object value, long time) {

    }

    @Override
    public void setExpire(String key, Object value, long time, TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {

    }

    @Override
    public void setExpire(String[] keys, Object[] values, long time) {

    }

    @Override
    public void set(String[] keys, Object[] values) {

    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public Set<String> keys(String keyPatten) {
        return null;
    }

    @Override
    public byte[] get(byte[] key) {
        return new byte[0];
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public Object get(String key, RedisSerializer<Object> valueSerializer) {
        return null;
    }

    @Override
    public HashOperations<String, String, Object> opsForHash() {
        return null;
    }

    @Override
    public void putHashValue(String key, String hashKey, Object hashValue) {

    }

    @Override
    public Object getHashValues(String key, String hashKey) {
        return null;
    }

    @Override
    public void delHashValues(String key, Object... hashKeys) {

    }

    @Override
    public Map<String, Object> getHashValue(String key) {
        return null;
    }

    @Override
    public void putHashValues(String key, Map<String, Object> map) {

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
