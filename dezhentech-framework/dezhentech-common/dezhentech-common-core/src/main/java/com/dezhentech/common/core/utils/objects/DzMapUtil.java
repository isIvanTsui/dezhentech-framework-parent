package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.map.MapUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * dzMap工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzMapUtil
 * @since 2022/11/10 20:41:45
 **/
public class DzMapUtil extends MapUtil {
	/**
	 * @param map    原Map
	 * @param symbol 分隔符
	 * @return {@code Map<K, V> }
	 * @descriptions 将已知Map转换为key为驼峰风格的Map<br>
	 * 如果KEY为非String类型，保留原值
	 * @author xu.zhang@dezhentech.com
	 * @time 2022/11/09 12:37:00
	 */
	public static <K, V> Map<K, V> toCamelCaseMap(Map<K, V> map, char symbol) {
		return (map instanceof LinkedHashMap) ? new DzCamelCaseLinkedMap<>(map, symbol) : new DzCamelCaseMap<>(map, symbol);
	}
}
