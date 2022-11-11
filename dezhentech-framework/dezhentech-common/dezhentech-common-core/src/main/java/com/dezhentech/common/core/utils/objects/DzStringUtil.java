package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.StrUtil;

/**
 * dz字符串工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzStringUtil
 * @since 2022/11/10 20:42:00
 **/
public class DzStringUtil extends StrUtil {


    /**
     * 字符串常量：减号（连接符） {@code "-"}
     */
    public static char C_DASHED = CharPool.DASHED;

    /**
     * 替换字符串中的第一个指定字符串
     *
     * @param str         字符串
     * @param searchStr   被查找的字符串
     * @param replacement 被替换的字符串
     * @return 替换后的字符串
     */
    public static String replaceFirst(CharSequence str, CharSequence searchStr, CharSequence replacement) {
        if (isEmpty(str) || isEmpty(searchStr)) {
            return str(str);
        }
        if (null == replacement) {
            replacement = EMPTY;
        }

        final int strLength = str.length();
        final int searchStrLength = searchStr.length();

        final StringBuffer result = new StringBuffer();
        int preIndex = 0;
        int index;
        if ((index = indexOf(str, searchStr, preIndex, false)) > -1) {
            result.append(str.subSequence(preIndex, index));
            result.append(replacement);
            preIndex = index + searchStrLength;
        }

        if (preIndex < strLength) {
            // 结尾部分
            result.append(str.subSequence(preIndex, strLength));
        }
        return result.toString();

    }

    public static char toChar(String str) {
        if (isEmpty(str)) {
            return '0';
        }
        return str.charAt(0);
    }

    /**
     * 将字符串转化为大写
     * @param value
     * @return
     */
    public static String toUpperCase(String value) {
        if (value == null) {
            return null;
        }
        return value.toUpperCase();
    }

    /**
     * 将字符串转化为小写
     * @param value
     * @return
     */
    public static String toLowerCase(String value) {
        if (value == null) {
            return null;
        }
        return value.toLowerCase();
    }

    /**
     * 当给定对象为null时，转换为Empty，不为null时，转为String
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(Object str) {
        return (str == null) ? EMPTY : trimToEmpty(str.toString());
    }

}
