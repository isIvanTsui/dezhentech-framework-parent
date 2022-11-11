package com.dezhentech.common.core.utils.properties;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.dezhentech.common.core.exceptions.ServiceException;
import com.dezhentech.common.core.utils.clazz.DzReflectUtil;
import com.dezhentech.common.core.utils.objects.DzBeanUtil;
import com.dezhentech.common.core.utils.objects.DzCamelCaseLinkedMap;
import com.dezhentech.common.core.utils.objects.DzCamelCaseMap;
import com.dezhentech.common.core.utils.objects.DzStringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * dzyml工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.properties.DzYmlUtil
 * @since 2022/11/10 20:44:45
 **/
public class DzYmlUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    private Map<String, Object> prop;
    private static DzYmlUtil dzYmlUtil = null;

    private static final Character SYMBOL = '-';
    private static final String[] SUFFIXES = {"-property", "-properties", "-config", "-configuration"};
    private static final String YML_FILE_NAME = "application.yml";

    /**
     * 默认yml文件，私有构造，禁止直接创建
     */
    private DzYmlUtil() {
        this(YML_FILE_NAME);
    }

    /**
     * @param ymlFileName yml路径和文件全名（无路径则从classpath获取）
     * @descriptions 创建一个DzYmlUtil的实例
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 11:48:37
     */
    public DzYmlUtil(String ymlFileName) {
        try {
//            YamlReader reader = new YamlReader(new FileReader(FileUtil.file(ymlFileName)));
//            Object object = reader.read();
//
//            prop = (Map<String, Object>)object;



            InputStream input = FileUtil.getInputStream(ymlFileName);
            prop = objectMapper.readValue(input, Map.class);

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
    public Object getProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * @param key          Key名
     * @param defaultValue 默认值
     * @return {@code Object }
     * @descriptions 根据属性名读取值，属性为null，则返回全部
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/09 11:44:36
     */
    public Object getProperty(String key, Object defaultValue) {
        Object value = null;
        if (key == null) {
            value = prop;
        } else {
            value = BeanUtil.getProperty(prop, key);
            if (value == null) {
                value = defaultValue;
            }
        }

        return value;
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
            prefix = AnnotationUtil.getAnnotationValue(clazz, ConfigurationProperties.class, "prefix");
            if (prefix == null) {
                //未定义注解，则获取类名中的前缀，如类名为AbcdConfig，则前缀为Abcd
                prefix = DzStringUtil.toSymbolCase(clazz.getSimpleName(), DzStringUtil.C_DASHED);
                if (DzStringUtil.endWithAny(prefix, SUFFIXES)) {
                    prefix = DzStringUtil.subBefore(prefix, DzStringUtil.C_DASHED, true);
                }
            }
        }

        Map map = (Map) dzYmlUtil.getProperty(prefix);

        map = toCamelCaseMap(map, SYMBOL);

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

}

