package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.map.CamelCaseMap;
import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * 驼峰Key风格的Map<br>
 * 对KEY转换为驼峰，get("int_value")和get("intValue")获得的值相同，put进入的值也会被覆盖
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author Looly
 * @since 4.0.7
 */
public class DzCamelCaseMap<K, V> extends CamelCaseMap<K, V> {
    private static final long serialVersionUID = -1L;

    private Character symbol;
    // ------------------------------------------------------------------------- Constructor start

    /**
     * 构造
     */
    public DzCamelCaseMap() {
        super();
    }

    /**
     * 构造
     */
    public DzCamelCaseMap(Character symbol) {
        super();
        this.symbol = symbol;
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public DzCamelCaseMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public DzCamelCaseMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public DzCamelCaseMap(Map<? extends K, ? extends V> m, Character symbol) {
        this(DEFAULT_LOAD_FACTOR, m, symbol);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     */
    public DzCamelCaseMap(float loadFactor, Map<? extends K, ? extends V> m, Character symbol) {
        this(m.size(), loadFactor);
        this.symbol = symbol;
        this.putAll(m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     */
    public DzCamelCaseMap(float loadFactor, Map<? extends K, ? extends V> m) {
        super(loadFactor, m);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public DzCamelCaseMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    // ------------------------------------------------------------------------- Constructor end

    /**
     * 将Key转为驼峰风格，如果key为字符串的话
     *
     * @param key KEY
     * @return 驼峰Key
     */
    @Override
    protected K customKey(Object key) {

        if (key instanceof CharSequence) {
            if (symbol == null) {
                key = StrUtil.toCamelCase((CharSequence) key);
            } else {
                key = StrUtil.toCamelCase((CharSequence) key, symbol);
            }
        }
        return (K) key;
    }
}

