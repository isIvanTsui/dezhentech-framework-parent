package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.map.CamelCaseLinkedMap;

import java.util.Map;

/**
 * 驼峰Key风格的LinkedHashMap<br>
 *  * 对KEY转换为驼峰，get("int_value")和get("intValue")获得的值相同，put进入的值也会被覆盖
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzCamelCaseLinkedMap
 * @since 2022/11/10 20:40:45
 **/
public class DzCamelCaseLinkedMap<K, V> extends CamelCaseLinkedMap<K, V> {

    /**
     * 转换分隔符
     */
    private Character symbol = '-';
    // ------------------------------------------------------------------------- Constructor start

    /**
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:37:03
     */
    public DzCamelCaseLinkedMap() {
        super();
    }

    /**
     * @param symbol 分隔符
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:37:01
     */
    public DzCamelCaseLinkedMap(Character symbol) {
        super();
        this.symbol = symbol;
    }

    /**
     * @param initialCapacity 初始大小
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:36:48
     */
    public DzCamelCaseLinkedMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param map Map
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:36:44
     */
    public DzCamelCaseLinkedMap(Map<? extends K, ? extends V> map) {
        super(map);
    }

    /**
     * @param map    Map
     * @param symbol 分隔符
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:36:18
     */
    public DzCamelCaseLinkedMap(Map<? extends K, ? extends V> map, Character symbol) {
        this(DEFAULT_LOAD_FACTOR, map, symbol);
    }

    /**
     * @param loadFactor 加载因子
     * @param map          Map
     * @param symbol     象征
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:35:19
     */
    public DzCamelCaseLinkedMap(float loadFactor, Map<? extends K, ? extends V> map, Character symbol) {
        this(map.size(), loadFactor);
        this.symbol = symbol;
        this.putAll(map);
    }

    /**
     * @param loadFactor 加载因子
     * @param map        Map
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:36:09
     */
    public DzCamelCaseLinkedMap(float loadFactor, Map<? extends K, ? extends V> map) {
        super(loadFactor, map);
    }

    /**
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     * @descriptions 构造
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:34:24
     */
    public DzCamelCaseLinkedMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    // ------------------------------------------------------------------------- Constructor end

    /**
     * @param key   key
     * @param value value
     * @return {@code V }
     * @descriptions key转驼峰，再put
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:34:28
     */
    @SuppressWarnings("unchecked")
    @Override
    public V put(K key, V value) {
        return super.put(customKey(key), value);
    }

    /**
     * @param key KEY
     * @return {@code K }
     * @descriptions 将Key转为驼峰风格，如果key为字符串的话
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 14:24:35
     */
    @Override
    protected K customKey(Object key) {
        if (key instanceof CharSequence) {
            if (symbol == null) {
                key = DzStringUtil.toCamelCase((CharSequence) key);
            } else {
                key = DzStringUtil.toCamelCase((CharSequence) key, symbol);
            }
        }

        return (K) key;
    }

}

