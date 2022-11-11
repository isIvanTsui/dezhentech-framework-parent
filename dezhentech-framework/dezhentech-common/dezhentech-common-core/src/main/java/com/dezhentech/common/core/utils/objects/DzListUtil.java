package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * dz List 工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzListUtil
 * @since 2022/11/10 20:38:18
 **/
public class DzListUtil extends ListUtil {


	/**
	 * @param clazz 类
	 * @return boolean
	 * @descriptions 判断指定类是否是List的子类或者父类
	 * @author xu.zhang@dezhentech.com
	 * @time 2022/11/09 12:41:21
	 */
	public static boolean isList(Class clazz) {
		try {
			return List.class.isAssignableFrom(clazz) || clazz.newInstance() instanceof List;
		} catch (Exception e) {
			return false;
		}
	}
}
