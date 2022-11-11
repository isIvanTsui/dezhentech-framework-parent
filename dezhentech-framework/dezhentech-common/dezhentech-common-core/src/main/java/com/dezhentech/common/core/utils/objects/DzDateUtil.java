package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Date工具<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzDateUtil
 * @since 2022/11/10 20:36:35
 **/
public class DzDateUtil extends DateUtil {

    public static Date parseDate(String str) {
        if (str == null) {
            return null;
        }
        return parse(str);
    }

    public static java.sql.Date parseSqlDate(String str) {
        return new java.sql.Date(parse(str).getTime());
    }

    public static Timestamp parseTimestamp(String str) {
        return new Timestamp(parse(str).getTime());
    }


    public static Time parseTime(String str) {
        return new Time(parse(str).getTime());
    }

    /**
     * 
     * 根据特定格式格式化日期<br/>
     * 
     * @param date 被格式化的日期
     * @param formats 日期格式的数组，依次尝试，常用格式见：  {@link DatePattern}
     * @return String 格式化后的字符串
     *
     */
    public static String format(final Date date, String... formats) {
        String dateStr = null;
        for (String format : formats) {
            dateStr = format(date, format);
            if (DzStringUtil.isNotEmpty(dateStr)) {
                break;
            }
        }

        return dateStr;
    }

}
