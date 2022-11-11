package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * dzNumber工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzNumberUtil
 * @since 2022/11/10 20:41:05
 **/
@Slf4j
public class DzNumberUtil extends NumberUtil {

    /**
     * @param obj 待转对象
     * @return {@code Integer }
     * @descriptions 将 对象 转化成 Integer 非数字对象返回为  null
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 01:06:25
     */
    public static Integer parseInteger(Object obj) {
        Integer rtn = null;
        if (obj != null) {
            try {
                rtn = Integer.valueOf(obj.toString());
            } catch (Exception e) {
                log.error("parseInteger()," + e.getMessage());
            }
        }
        return rtn;
    }

    /**
     * @param obj 待转对象
     * @return {@code Long }
     * @descriptions 将 对象 转化成 Long 非数字对象返回为  null
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 01:06:40
     */
    public static Long parseLong(Object obj) {
        Long rtn = null;
        if (obj != null) {
            try {
                rtn = Long.valueOf(obj.toString());
            } catch (Exception e) {
                log.error("parseLong()," + e.getMessage());
            }
        }
        return rtn;
    }


    /**
     * @param obj 待转对象
     * @return {@code Double }
     * @descriptions 将 对象 转化成 Double 非数字对象返回为  null
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 01:06:51
     */
    public static Double parseDouble(Object obj) {
        Double rtn = null;
        if (obj != null) {
            try {
                rtn = Double.valueOf(obj.toString());
            } catch (Exception e) {
                log.error("parseDouble()," + e.getMessage());
            }
        }
        return rtn;
    }

    /**
     * @param obj 待转对象
     * @return {@code Float }
     * @descriptions 将 对象 转化成 Float 非数字对象返回为  null
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 01:06:55
     */
    public static Float parseFloat(Object obj) {
        Float rtn = null;
        if (obj != null) {
            try {
                rtn = Float.valueOf(obj.toString());
            } catch (Exception e) {
                log.error("parseFloat()," + e.getMessage());
            }
        }
        return rtn;
    }

    /**
     * @param obj 待转对象
     * @return {@code Short }
     * @descriptions 将 对象 转化成 Short 非数字对象返回为  null
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 01:07:16
     */
    public static Short parseShort(Object obj) {
        Short rtn = null;
        if (obj != null) {
            try {
                rtn = Short.valueOf(obj.toString());
            } catch (Exception e) {
                log.error("parseShort()," + e.getMessage());
            }
        }
        return rtn;
    }

    /**
     * @param obj 待转对象
     * @return {@code Byte }
     * @descriptions 将 对象 转化成 Byte 非数字对象返回为  null
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 01:07:35
     */
    public static Byte parseByte(Object obj) {
        Byte rtn = null;
        if (obj != null) {
            try {
                rtn = Byte.valueOf(obj.toString());
            } catch (Exception e) {
                log.error("parseByte()," + e.getMessage());
            }
        }
        return rtn;
    }
}
