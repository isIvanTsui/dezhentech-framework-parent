package com.dezhentech.common.core.utils.yml;


import cn.hutool.extra.expression.engine.spel.SpELEngine;
import com.dezhentech.common.core.exceptions.ServiceException;
import com.dezhentech.common.core.utils.annotation.DzAnnotationUtil;
import com.dezhentech.common.core.utils.clazz.DzReflectUtil;
import com.dezhentech.common.core.utils.io.DzFileUtil;
import com.dezhentech.common.core.utils.objects.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * dzyml工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.yml.DzYmlUtil
 * @since 2022/11/10 20:44:45
 **/
public class DzYmlUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    private static SpELEngine spelEngine = new SpELEngine();
    private Map<String, Object> prop;
    private static DzYmlUtil dzYmlUtil = null;

    private static final String[] CONFIG_CLASS_SUFFIXES = {"-property", "-properties", "-config", "-configuration"};
    private static final String PATH_ACTIVE = "spring.profiles.active";
    private static final String PATH_INCLUDE = "spring.profiles.include";
//
//    /**
//     * 环境配置路径的键值
//     */
//    @Setter
//    @Getter
//    private List<String> active;
//    /**
//     * 引入yml的键值
//     */
//    @Setter
//    @Getter
//    private List<String> include;
    /**
     * 配置文件的前缀
     */
    private static final String PREFIX = "application";

    /**
     * 配置文件的后缀
     */
    private static final String SUFFIX = "yml";

    /**
     * 默认application.yml文件
     * 自动加载active和include，支持表达式
     * 私有构造，禁止直接创建，请使用getInstance()
     */
    private DzYmlUtil() {
        this(DzStringUtil.format("{}.{}", PREFIX, SUFFIX));
    }

    /**
     * dzyml工具
     *
     * @param path yml路径和文件全名（无路径则从classpath获取）
     * @return {@code  }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/11 10:18:33
     */
    public DzYmlUtil(String path) {
        try {
            // 本工具规则：
            // 1、spring.profile.include的属性会覆盖默认属性
            // 2、spring.profiles.active会覆盖spring.profile.include和默认属性。
            // 3、spring.profile.include和spring.profiles.active含多个值（用半角逗号分隔）时，后面文件的属性覆盖前面的相同属性

            //因此，解析顺序为：
            // 1、读取默认配置
            // 2、读取引入的yml（spring.profiles.include用于引入公共的配置文件）
            // 3、读取启用的yml（spring.profiles.active用于切换多环境）

            // 组合一个map，把启用的配置，引入的文件组合起来
            Map<String, Object> mapAll = new LinkedHashMap<>();

            // 1、先读取默认配置
            //Map<String, Object> mainMap = yml2Map(DzStringUtil.format("{}.{}", this.prefix, this.suffix));
            Map<String, Object> mainMap = yml2Map(path);
            mapAll.putAll(mainMap);

            // 读取启用的配置
            if (mainMap != null) {

                // 2、读取引入的yml（用于引入公共的配置文件）
                Object include = DzBeanUtil.getProperty(mainMap, PATH_INCLUDE);
                if (DzObjectUtil.isNotEmpty(include)) {
                    // include是使用逗号分隔开的，需要切割一下
                    List<String> includeList = DzStringUtil.split(DzStringUtil.toString(include), DzStringUtil.C_COMMA, true, true);
                    for (String inc : includeList) {
                        mapAll.putAll(yml2Map(DzStringUtil.format("{}-{}.{}", PREFIX, inc, SUFFIX)));
                    }
                }

                // 3、读取启用的yml（spring.profiles.active用于切换多环境）
                Object active = DzBeanUtil.getProperty(mainMap, PATH_ACTIVE);
                if (DzObjectUtil.isNotEmpty(active)) {
                    // active是使用逗号分隔开的，需要切割一下
                    List<String> activeList = DzStringUtil.split(DzStringUtil.toString(active), DzStringUtil.C_COMMA, true, true);
                    for (String act : activeList) {
                        mapAll.putAll(yml2Map(DzStringUtil.format("{}-{}.{}", PREFIX, act, SUFFIX)));
                    }
                }

                // 把map转化为字符串
                //String mapString = MapUtil.joinIgnoreNull(mapAll, "\n", "=");
                // 再把map字符串转化为yamlStr字符串
                //String yamlStr = properties2YamlStr(mapString);
                // 使用Yaml构建LinkedHashMap
                //return super.loadAs(yamlStr, LinkedHashMap.class);


            }
            //执行SpEL表达式
            mapAll = express(mapAll, mapAll);

            prop = mapAll;

        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }


    /**
     * 获取单例
     */
    public static DzYmlUtil getInstance() {
        if (dzYmlUtil == null) {
            dzYmlUtil = new DzYmlUtil();
        }
        return dzYmlUtil;
    }

    /**
     * @return {@code Object }
     * @descriptions 根据属性名读取值
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 11:44:36
     */
    public Object getProperty() {
        return getProperty(null, null);
    }

    /**
     * @param key Key名
     * @return {@code Object }
     * @descriptions 根据属性名读取值
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 11:44:36
     */
    public <T> T getProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * 得到财产
     *
     * @param key          Key名
     * @param defaultValue 默认值
     * @return {@code T }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/11 10:38:48
     */
    public <T> T getProperty(String key, Object defaultValue) {
        Object value = null;
        if (key == null) {
            value = prop;
        } else {
            value = DzBeanUtil.getProperty(prop, key);
            if (value == null) {
                value = defaultValue;
            }
        }

        return (T) value;
    }


    /**
     * @param key Key名
     * @return boolean
     * @descriptions 是否存在key
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 11:43:18
     */
    public boolean hasKey(String key) {
        Object value = getProperty(key);
        return value != null;
    }

    /**
     * @param clazz clazz
     * @return {@code T }
     * @descriptions 自动配置
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 11:42:42
     */
    public static <T> T autoConfig(Class<T> clazz) {
        return autoConfig(clazz, DzYmlUtil.getInstance());

    }

    /**
     * @param clazz       clazz
     * @param ymlFileName yml文件名称
     * @return {@code T }
     * @descriptions 自动配置
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 11:43:46
     */
    public static <T> T autoConfig(Class<T> clazz, String ymlFileName) {
        return autoConfig(clazz, new DzYmlUtil(ymlFileName));
    }

    /**
     * @param clazz     clazz
     * @param dzYmlUtil dz yml工具
     * @return {@code T }
     * @descriptions 自动配置
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 12:07:16
     */
    private static <T> T autoConfig(Class<T> clazz, DzYmlUtil dzYmlUtil) {
        return autoConfig(clazz, dzYmlUtil, null);
    }

    /**
     * @param clazz     clazz
     * @param dzYmlUtil dz yml工具
     * @param prefix    前缀
     * @return {@code T }
     * @descriptions 自动配置
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 12:07:44
     */
    private static <T> T autoConfig(Class<T> clazz, DzYmlUtil dzYmlUtil, String prefix) {

        T bean = DzReflectUtil.newInstance(clazz);
        if (prefix == null) {
            //未定义前缀，则获取注解
            prefix = DzAnnotationUtil.getAnnotationValue(clazz, ConfigurationProperties.class, "prefix");
            if (prefix == null) {
                //未定义注解，则获取类名中的前缀，如类名为AbcdConfig，则前缀为Abcd
                prefix = DzStringUtil.toSymbolCase(clazz.getSimpleName(), DzStringUtil.C_DASHED);
                if (DzStringUtil.endWithAny(prefix, CONFIG_CLASS_SUFFIXES)) {
                    prefix = DzStringUtil.subBefore(prefix, DzStringUtil.C_DASHED, true);
                }
            }
        }

        Map map = (Map) dzYmlUtil.getProperty(prefix);

        map = toCamelCaseMap(map, DzStringUtil.C_DASHED);

        bean = DzBeanUtil.fillBeanWithMap(map, bean, true, false);

        return bean;
    }

    /**
     * @param map    原Map
     * @param symbol 分隔符
     * @return {@code Map<K, V> }
     * @descriptions 将已知Map转换为key为驼峰风格的Map<br>
     * 如果KEY为非String类型，保留原值
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 12:12:07
     */
    private static <K, V> Map<K, V> toCamelCaseMap(Map<K, V> map, Character symbol) {
        Map resultMap = (map instanceof LinkedHashMap) ? new DzCamelCaseLinkedMap<>(symbol) : new DzCamelCaseMap<>(symbol);
        for (K key : map.keySet()) {
            V value = map.get(key);
            V v = value;
            if (value instanceof Map) {
                v = (V) toCamelCaseMap((Map) value, symbol);
            }
            resultMap.put(key, v);
        }

        return resultMap;
    }

    /**
     * Yml文件 转 LinkedHashMap
     *
     * @param path Yml路径
     * @return {@code Map<String, Object> }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/11 09:32:22
     */
    public Map<String, Object> yml2Map(String path) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        InputStream input = DzFileUtil.getInputStream(path);
        try {
            map = objectMapper.readValue(input, LinkedHashMap.class);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }

        return map;

    }

    /**
     * 表达式解析
     *
     * @param map     map
     * @param context 上下文
     * @return {@code Map<String, Object> }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/11 10:22:00
     */
    public Map<String, Object> express(Map<String, Object> map, Map<String, Object> context) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (DzObjectUtil.isNotEmpty(value)) {
                if (value instanceof Map) {
                    entry.setValue(express((Map) value, context));
                }

                if (value instanceof List) {
                    entry.setValue(express((List) value, context));
                }

                if (value instanceof String && DzStringUtil.contains((String) value, "${")) {
                    entry.setValue(express((String) value, context));

                }
            }

        }
        return map;
    }

    /**
     * 表达式解析
     *
     * @param list    list
     * @param context 上下文
     * @return {@code List }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/11 10:22:43
     */
    public List express(List list, Map<String, Object> context) {
        for (int i = 0; i < list.size(); i++) {
            Object value = list.get(i);
            if (DzObjectUtil.isNotEmpty(value)) {
                if (value instanceof Map) {
                    express((Map) value, context);
                }
                if (value instanceof List) {
                    express((List) value, context);
                }

                if (value instanceof String && DzStringUtil.contains((String) value, "${")) {
                    //list.set(i, spelEngine.eval((String) value, context));
                    list.set(i, express((String) value, context));
                }
            }

        }
        return list;
    }

    public Object express(String expression, Map<String, Object> context) {
        Object rtn = expression;
        Map simpleMap = new HashMap<String, Object>();
        String[] exps = DzStringUtil.subBetweenAll((String) expression, "${", "}");

        if (exps != null) {
            for (String exp : exps) {
                //simpleMap.put(exp, DzBeanUtil.getProperty(context, exp));
                Object value = DzBeanUtil.getProperty(context, exp);
                if (expression.length() != exp.length() + 3) {
                    rtn = DzStringUtil.replace(DzStringUtil.toString(rtn), "${" + exp + "}", DzStringUtil.toString(value));
                } else {
                    rtn = value;
                }
            }

        }

        return rtn;
    }

}

