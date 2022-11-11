package com.dezhentech.common.core.utils.properties;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.setting.dialect.Props;
import com.dezhentech.common.core.utils.clazz.DzReflectUtil;
import com.dezhentech.common.core.utils.objects.DzStringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.lang.reflect.Field;

/**
 * dzProperties工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.properties.DzPropertiesUtil
 * @since 2022/11/10 20:38:41
 **/
public class DzPropertiesUtil {
    private static final String PROPERTIES_FILE_NAME = "application.properties";

    public static <T> T autoConfig(Class<T> clazz) {
        return autoConfig(clazz, PROPERTIES_FILE_NAME);
    }

    public static <T> T autoConfig(Class<T> clazz, String propertiesFileName) {
        Props props = new Props(propertiesFileName);

        T bean = DzReflectUtil.newInstance(clazz);
        String prefix = AnnotationUtil.getAnnotationValue(clazz, ConfigurationProperties.class, "prefix") + DzStringUtil.DOT;

        Field[] fields = DzReflectUtil.getFields(clazz);
        for (Field field : fields) {
            Object value = DzReflectUtil.getFieldValue(bean, field);

            String fieldName = field.getName();

            boolean isContains = false;
            if (props.containsKey(prefix + fieldName)) {
                isContains = true;
            } else {
                fieldName = DzStringUtil.toSymbolCase(field.getName(), '-');
                if (props.containsKey(prefix + fieldName)) {
                    isContains = true;
                } else {
                    fieldName = DzStringUtil.toUnderlineCase(fieldName);
                    if (props.containsKey(prefix + fieldName)) {
                        isContains = true;
                    }
                }
            }

            if (isContains) {
                String valueString = null;
                if (value != null) {
                    valueString = value.toString();
                }
                valueString = props.getStr(prefix + fieldName, valueString);
                value = Convert.convert(field.getType(), valueString);

                DzReflectUtil.setFieldValue(bean, field, value);
            }

        }
        return bean;
    }
}
