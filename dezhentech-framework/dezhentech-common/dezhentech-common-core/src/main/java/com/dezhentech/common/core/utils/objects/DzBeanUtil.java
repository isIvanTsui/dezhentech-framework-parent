package com.dezhentech.common.core.utils.objects;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.setting.dialect.Props;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * dzBean工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzBeanUtil
 * @since 2022/11/10 17:43:11
 **/
public class DzBeanUtil extends BeanUtil {

    /**
     * @param str   str
     * @param clazz clazz
     * @return {@code T }
     * @descriptions 基本类型转换
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 15:38:00
     */
    public static <T> T converter(String str, Class<T> clazz) {
        Object value = null;

        try {
            if (clazz == String.class) {
                value = str;
            } else {
                if (str != null) {
                    str = DzStringUtil.trim(str);

                    if (clazz == Integer.class || clazz == int.class) {
                        value = DzNumberUtil.parseInt(str);
                    } else if (clazz == Long.class || clazz == long.class) {
                        value = DzNumberUtil.parseLong(str);
                    } else if (clazz == Short.class || clazz == short.class) {
                        value = DzNumberUtil.parseShort(str);
                    } else if (clazz == Byte.class || clazz == byte.class) {
                        value = DzNumberUtil.parseByte(str);
                    } else if (clazz == Float.class || clazz == float.class) {
                        value = DzNumberUtil.parseFloat(str);
                    } else if (clazz == Double.class || clazz == double.class) {
                        value = DzNumberUtil.parseDouble(str);
                    } else if (clazz == BigInteger.class) {
                        value = BigInteger.valueOf(DzNumberUtil.parseLong(str));
                    } else if (clazz == BigDecimal.class) {
                        value = BigDecimal.valueOf(DzNumberUtil.parseDouble(str));
                    } else if (clazz == Character.class || clazz == char.class) {
                        value = DzStringUtil.toChar(str);
                    } else if (clazz == Boolean.class || clazz == boolean.class) {
                        value = DzBooleanUtil.parseBoolean(str);
                    } else if (clazz == Timestamp.class) {
                        value = DzDateUtil.parseTimestamp(str);
                    } else if (clazz == Time.class) {
                        value = DzDateUtil.parseTime(str);
                    } else if (clazz == java.sql.Date.class) {
                        value = DzDateUtil.parseSqlDate(str);
                    } else if (clazz == Date.class) {
                        value = DzDateUtil.parseDate(str);
                    } else if (clazz == Byte[].class || clazz == byte[].class) {
                        value = str.getBytes();
                    } else if (clazz == String[].class) {
                        value = str.split(",");
                    } else if (clazz == int[].class) {
                        String[] arr = str.split(",");
                        int[] intarr = new int[arr.length];
                        for (int i = 0; i < arr.length; i++) {
                            intarr[i] = converter(arr[i], Integer.class);
                        }
                        value = intarr;
                    } else {
                        throw new BeanException("Unknown java type: " + clazz + " , cannot convert!");
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanException("Data type convert error: class [" + clazz + "], string [" + str + "] .", e);
        }
        return (T) value;
    }

    /**
     * @param obj obj
     * @return {@code String }
     * @descriptions 基本类型转换
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 15:37:41
     */
    public static <T> String converter(T obj) {


        String value = null;

        if (obj != null) {
            if (obj instanceof String || obj instanceof Character) {
                value = obj.toString();
            } else if (obj instanceof Number || obj instanceof Boolean) {
                value = obj.toString();
            } else if (obj instanceof Date) {
                Date date = (Date) obj;

                Props props = new Props("system.properties");
                String MAPPING_PREFIX = "mapping.";
                String[] datetimeFormat = props.getStr(MAPPING_PREFIX + "datetimeFormat").split(",");

                value = DzDateUtil.format(date, datetimeFormat);
            } else if (obj instanceof byte[]) {
                value = new String((byte[]) obj);
            } else {
                value = obj.toString();
                // throw new BeanException("Unknown java type: " + clazz + " ,
                // cannot convert!");
            }

        }
        return value;
    }

    /**
     * @param str  str
     * @param type 类型
     * @return {@code Object }
     * @descriptions 基本类型转换
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 15:37:00
     */
    public static Object converter(String str, String type) {

        Object value = null;

        if (str != null && type != null) {
            Class<?> clazz = getJavaDataType(type);
            value = converter(str, clazz);
        }
        return value;
    }

    /**
     * @param dbDataType
     * @return {@code Class<?> }
     * @descriptions 获取java数据类型
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 15:37:05
     */
    public static Class<?> getJavaDataType(String dbDataType) {
        Class<?> clazz = String.class;
        dbDataType = DzStringUtil.toLowerCase(dbDataType);
        dbDataType = dbDataType.replaceAll("\\(.*\\)", "");

        //String javatype = ConfigUtils.getMappingJavaDataType(dbDataType);
        Props props = new Props("system.properties");
        String javatype = props.getStr(dbDataType);
        if (DzStringUtil.isNotBlank(javatype)) {
            try {
                if (javatype.endsWith("[]")) {
                    javatype = javatype.replaceAll("\\[.*\\]", "");
                    clazz = Class.forName(javatype);
                    clazz = Array.newInstance(clazz, 1).getClass();
                } else {
                    clazz = Class.forName(javatype);
                }

            } catch (ClassNotFoundException e) {
                throw new BeanException(e);
            }
        } else {
            //LOGGER.warn("Undefine db type: " + dbDataType + ", data will be converted to java.lang.String!");
        }
        return clazz;
    }


}
