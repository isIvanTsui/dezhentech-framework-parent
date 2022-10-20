package com.dezhentech.framework.cache.api;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存中心统一工具
 *
 * @author yfcui
 * @date 2022/10/20
 */
public interface DzCacheUtil {
    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间(单位秒)
     */
    void setExpire(byte[] key, byte[] value, long time);

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key      redis主键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 过期时间单位
     */
    void setExpire(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 获取剩余时间
     *
     * @param key      redis主键
     * @param timeUnit 时间单位
     * @return long 剩余时间
     */
    long getExpire(String key, TimeUnit timeUnit);

    void setExpire(String key, Object value, long time);

    void setExpire(String key, Object value, long time, TimeUnit timeUnit, RedisSerializer<Object> valueSerializer);


    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     *
     * @param keys   redis主键数组
     * @param values 值数组
     * @param time   过期时间(单位秒)
     */
    void setExpire(String[] keys, Object[] values, long time);


    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     *
     * @param keys   the keys
     * @param values the values
     */
    void set(String[] keys, Object[] values);


    /**
     * 添加到缓存
     *
     * @param key   the key
     * @param value the value
     */
    void set(String key, Object value);

    /**
     * 查询在以keyPatten的所有  key
     *
     * @param keyPatten the key patten
     * @return the set
     */
    Set<String> keys(String keyPatten);

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the byte [ ]
     */
    byte[] get(byte[] key);

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the string
     */
    Object get(String key);

    /**
     * 根据key获取对象
     *
     * @param key             the key
     * @param valueSerializer 序列化
     * @return the string
     */
    Object get(String key, RedisSerializer<Object> valueSerializer);

    /**
     * Ops for hash hash operations.
     *
     * @return the hash operations
     */
    HashOperations<String, String, Object> opsForHash();

    /**
     * 对HashMap操作
     *
     * @param key       the key
     * @param hashKey   the hash key
     * @param hashValue the hash value
     */
    void putHashValue(String key, String hashKey, Object hashValue);

    /**
     * 获取单个field对应的值
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the hash values
     */
    Object getHashValues(String key, String hashKey);

    /**
     * 根据key值删除
     *
     * @param key      the key
     * @param hashKeys the hash keys
     */
    void delHashValues(String key, Object... hashKeys);

    /**
     * key只匹配map
     *
     * @param key the key
     * @return the hash value
     */
    Map<String, Object> getHashValue(String key);

    /**
     * 批量添加
     *
     * @param key the key
     * @param map the map
     */
    void putHashValues(String key, Map<String, Object> map);

    /**
     * 集合数量
     *
     * @return the long
     */
    long dbSize();

    /**
     * 清空redis存储的数据
     *
     * @return the string
     */
    String flushDB();

    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    boolean exists(String key);


    /**
     * 删除key
     *
     * @param keys the keys
     * @return the long
     */
    boolean del(String... keys);

    /**
     * 对某个主键对应的值加一,value值必须是全数字的字符串
     *
     * @param key the key
     * @return the long
     */
    long incr(String key);

    /**
     * redis List 引擎
     *
     * @return the list operations
     */
    ListOperations<String, Object> opsForList();

    /**
     * redis List数据结构 : 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    Long leftPush(String key, Object value);

    /**
     * redis List数据结构 : 移除并返回列表 key 的头元素
     *
     * @param key the key
     * @return the string
     */
    Object leftPop(String key);

    /**
     * redis List数据结构 :将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    Long in(String key, Object value);

    /**
     * redis List数据结构 : 移除并返回列表 key 的末尾元素
     *
     * @param key the key
     * @return the string
     */
    Object rightPop(String key);


    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     *
     * @param key the key
     * @return the long
     */
    Long length(String key);


    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   the key
     * @param i     the
     * @param value the value
     */
    void remove(String key, long i, Object value);

    /**
     * redis List数据结构 : 将列表 key 下标为 index 的元素的值设置为 value
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    void set(String key, long index, Object value);

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<Object> getList(String key, int start, int end);

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key             the key
     * @param start           the start
     * @param end             the end
     * @param valueSerializer 序列化
     * @return the list
     */
    List<Object> getList(String key, int start, int end, RedisSerializer<Object> valueSerializer);

    /**
     * redis List数据结构 : 批量存储
     *
     * @param key  the key
     * @param list the list
     * @return the long
     */
    Long leftPushAll(String key, List<String> list);

    /**
     * redis List数据结构 : 将值 value 插入到列表 key 当中，位于值 index 之前或之后,默认之后。
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    void insert(String key, long index, Object value);

    byte[] rawKey(Object key);

    byte[] rawValue(Object value, RedisSerializer valueSerializer);

    Object deserializeValue(byte[] value, RedisSerializer<Object> valueSerializer);
}
