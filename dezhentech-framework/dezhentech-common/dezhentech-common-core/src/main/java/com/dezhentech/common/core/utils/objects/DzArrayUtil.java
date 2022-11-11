package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * dz数组工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzArrayUtil
 * @since 2022/11/10 20:35:58
 **/
public class DzArrayUtil extends ArrayUtil {

    /**
     * @param arrays 数组
     * @return {@code String[] }
     * @descriptions 去掉字符串数组中的null元素
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 12:40:30
     */
    public static String[] getNotNullArray(String[] arrays){
        List<String> l=new ArrayList<String>();
        for(String str:arrays){
            if(str != null){
                l.add(str);
            }
        }
        String[] rtn=new String[l.size()];
        l.toArray(rtn);
        return rtn;
    }
}
