package com.dezhentech.common.core.utils.objects;


import cn.hutool.core.util.ObjectUtil;
import com.dezhentech.common.core.utils.json.DzJsonUtil;
import com.dezhentech.common.core.utils.serializer.DzKryoUtil;

/**
 * dz对象工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.objects.DzObjectUtil
 * @since 2022/11/10 17:43:04
 **/
public class DzObjectUtil extends ObjectUtil {
    public static final DzNull NULL = new DzNull();

    public static String toString(Object obj) {
        String result = null;
        if (obj == null) {
            result = DzStringUtil.EMPTY;
        } else {
            result = obj.toString();
            String className = obj.getClass().getName();
            if (result.startsWith(className)) {
                result = toJson(obj);
            }
        }
        return result;
    }

    public static String toJson(Object obj) {
        // return ToStringBuilder.reflectionToString(obj,
        // ToStringStyle.JSON_STYLE);
        String rnt = null;
        try {
            rnt = DzJsonUtil.toJSONString(obj);
        } catch (Exception e) {
            //LOGGER.error(e.getMessage(), e);
        }
        return rnt;
    }

    public enum SERIALIZE_TYPE {
        JSON, JDK, KRYO, DEFAULT
    }

    ;

    public static SERIALIZE_TYPE serializeType = SERIALIZE_TYPE.DEFAULT;

    public static <T> T serializeU(Object source) {
        return serialize(source, serializeType);
    }

    public static <T> T deserializeU(Object source) {
        return deserialize(source, serializeType);
    }

    /**
     * 通用序列化<br/>
     *
     * @param source
     * @param type
     * @return {@code T }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 20:43:32
     */
    public static <T> T serialize(Object source, SERIALIZE_TYPE type) {
        T obj = null;
        switch (type) {
            case DEFAULT:
            case JSON:
                obj = (T) serializationByJson(source);
                break;
            case JDK:
                obj = (T) serialize(source);
                break;
            case KRYO:
                obj = (T) serializationByKryo(source);
                break;
            default:

        }

        return obj;
    }

    /**
     * 通用反序列化<br/>
     *
     * @param <T>
     * @param source
     * @param type
     * @return T
     * @author zzz
     * @version 1.0.0.0, 2021年8月26日 下午2:05:04
     * @修改人 ：zzz
     * @修改时间：2021年8月26日 下午2:05:04
     * @修改备注：
     */
    public static <T> T deserialize(Object source, SERIALIZE_TYPE type) {
        T obj = null;
        switch (type) {
            case DEFAULT:
            case JSON:
                obj = deserializationByJson((String) source);
                break;
            case JDK:
                obj = deserialize((byte[]) source);
                break;
            case KRYO:
                obj = deserializationByKryo((String) source);
                break;
            default:

        }

        return obj;
    }

    /**
     * 序列化
     *
     * @param obj 待序列化对象
     * @return base64字符串
     */
    public static <T> String serializationByKryo(T obj) {
        return DzKryoUtil.writeToString(obj);
    }

    /**
     * 反序列化
     *
     * @param str 进行反序列化的base64字符串（Class必须和序列化的完全相同）
     * @return 对象
     */
    public static <T> T deserializationByKryo(String str) {
        return DzKryoUtil.readFromString(str);
    }

    public static <T> String serializationByJson(T obj) {
        return DzJsonUtil.toClassJSONString(obj);
    }

    public static <T> T deserializationByJson(String str) {
        T obj = null;

        obj = DzJsonUtil.classJSONStringtoJavaObject(str);

        return obj;
    }

    //    /**
    //     * 反序列化
    //     *
    //     * @param bytes
    //     * @return
    //     * @throws IOException
    //     * @throws ClassNotFoundException
    //     */
    //    public static <T> T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
    //        T obj = null;
    //        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    //        ObjectInputStream ois = new ObjectInputStream(bis);
    //        obj = (T) ois.readObject();
    //        ois.close();
    //        bis.close();
    //        return obj;
    //    }
    //
    //    /**
    //     * 序列化
    //     *
    //     * @param obj
    //     * @return
    //     * @throws IOException
    //     */
    //    public static byte[] serialize(Object obj) throws IOException {
    //        byte[] bytes = null;
    //        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    //
    //        ObjectOutputStream oos = new ObjectOutputStream(bos);
    //        oos.writeObject(obj);
    //        oos.flush();
    //        bytes = bos.toByteArray();
    //        oos.close();
    //        bos.close();
    //
    //        return bytes;
    //    }
}
