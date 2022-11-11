package com.dezhentech.common.core.utils.objects;

/**
 * dz布尔工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzBooleanUtil
 * @since 2022/11/10 20:39:54
 **/
public class DzBooleanUtil {

	public static Boolean parseBoolean(Object value){
		Boolean rtn = false;
		if(value!=null){
			if(value instanceof Boolean){
				rtn = (Boolean)value;
			}else{
				rtn = Boolean.valueOf(value.toString());
			}
		}
		return rtn;
	}
}
