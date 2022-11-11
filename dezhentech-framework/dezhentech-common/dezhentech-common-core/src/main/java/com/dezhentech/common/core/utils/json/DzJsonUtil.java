package com.dezhentech.common.core.utils.json;

import cn.hutool.core.util.ClassUtil;
import com.dezhentech.common.core.utils.objects.DzBeanUtil;
import com.dezhentech.common.core.utils.objects.DzStringUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.iceolive.util.StringUtil;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * JSON工具<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.json.DzJsonUtil
 * @since 2022/11/10 20:38:02
 **/
@Slf4j
public class DzJsonUtil {
    public final static String           BODY_KEY    = "body";

    // 加载速度太慢了，放在静态代码块中
    // private static final ObjectMapper mapper = new ObjectMapper();
    private static ObjectMapper          objectMapper;
    private static XmlMapper xmlMapper = new XmlMapper();

    private static Configuration         jsonPathAsPathConfig;
    private static Configuration         jsonPathAsValueConfig;

    private static Map<String, JsonPath> jsonPathMap = new ConcurrentHashMap<>();
    /**
     * 设置一些通用的属性
     */
    static {
        //Jackjson config
        objectMapper = new ObjectMapper();

        //将FastJson注册为Jackson的实体对象 
        //mapper.registerModule(new FastJsonModule());

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        // 如果无法序列化则返回空字串，不报错
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 如果存在未知属性，则忽略不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许key没有双引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许key有单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许整数以0开头
        objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        // 允许字符串中存在回车换行控制符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        //JSON-Path config
        Configuration.setDefaults(new Configuration.Defaults() {

            //            private final JsonProvider jsonProvider = new JacksonJsonNodeJsonProvider();
            private final JsonProvider    jsonProvider    = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        //JSON-Path cache
        //CacheProvider.setCache(cache);

        jsonPathAsPathConfig = Configuration.builder()
                .options(Option.AS_PATH_LIST, Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS, Option.DEFAULT_PATH_LEAF_TO_NULL).build();

        jsonPathAsValueConfig = Configuration.builder().options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS, Option.DEFAULT_PATH_LEAF_TO_NULL)
                .build();
        //                Configuration.defaultConfiguration()
        //                .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
        //                .addOptions(Option.ALWAYS_RETURN_LIST);
    }

    //    public static JSONObject toFastJsonObject(String json) throws Throwable {
    //        mapper.registerModule(new FastJsonModule());
    //        
    //        JSONObject jsonObject = mapper.readValue(json, JSONObject.class); // read from a source
    //        String jsonString = mapper.writeValueAsString(jsonObject); // output as String
    //        
    //        Date value = mapper.convertValue(jsonObject, Date.class);
    //        JSONObject jsonObject = mapper.convertValue(value, JSONObject.class);
    //        
    //        return jsonObject;
    //    }

    //////////////////////////////////////////////////////////////JSON方法

    public static String toClassJSONString(Object obj) {
    	DzJsonClassObject classObj = new DzJsonClassObject();
    	classObj.setClassName(obj.getClass().getName());
    	classObj.setObjectJson(toJSONString(obj));
        return obj != null ? toJSONString(classObj, () -> "") : "";
    }
    
    public static <T> T classJSONStringtoJavaObject(String classJSONString) {
    	T obj = null;
    	DzJsonClassObject classObj = toJavaObject(classJSONString, DzJsonClassObject.class);
    	
    	Class<T> tClass = null;
    	String className = classObj.getClassName();
    	if (StringUtil.isNotBlank(className)) {
    		tClass = ClassUtil.loadClass(className);
    		obj = toJavaObject(classObj.getObjectJson(), tClass);
    	} 
    	
    	//Class<T> tClass = (Class<T>) JsonUtil.class.getClassLoader().loadClass(className);
    	
        return obj;
    }
    
    public static String toJSONString(Object obj) {
        return obj != null ? toJSONString(obj, () -> "") : "";
    }

    public static String toJSONString(Object obj, Supplier<String> defaultSupplier) throws RuntimeException {
        if (obj instanceof ObjectMapper) {
            return "ObjectMapper: " + obj.toString();
        }
        //        if (obj instanceof Object[]) {
        //            obj = Arrays.asList((Object[])obj);
        //        }
        try {
            if (objectMapper.canSerialize(obj.getClass())) {
                return obj != null ? objectMapper.writeValueAsString(obj) : defaultSupplier.get();
            }
        } catch (Throwable e) {
            obj = DzBeanUtil.beanToMap(obj);
            return toJSONString(obj);
            //log.error(String.format("toJSONString %s", obj != null ? obj.toString() : "null"), e);
            //throw new RuntimeException(e.getMessage());
        }
        return defaultSupplier.get();
    }
    
//    public static <T> T parseObject(String value, Class<T> tClass) {
//        return DzStringUtil.isNotBlank(value) ? toJavaObject(value, tClass, () -> null) : null;
//    }
    
    public static <T> T toJavaObject(String value, Class<T> tClass) {
        return DzStringUtil.isNotBlank(value) ? toJavaObject(value, tClass, () -> null) : null;
    }
    


    public static <T> T toJavaObject(Object obj, Class<T> tClass) {
        return obj != null ? toJavaObject(toJSONString(obj), tClass, () -> null) : null;
    }

    public static <T> T toJavaObject(String value, Class<T> tClass, Supplier<T> defaultSupplier) {
        try {
            if (DzStringUtil.isBlank(value)) {
                return defaultSupplier.get();
            }
            return objectMapper.readValue(value, tClass);
        } catch (Throwable e) {
            log.error(String.format("toJavaObject exception: \n %s\n %s", value, tClass), e);
        }
        return defaultSupplier.get();
    }

    // 简单地直接用json复制或者转换(Cloneable)
    public static <T> T jsonCopy(Object obj, Class<T> tClass) {
        return obj != null ? toJavaObject(toJSONString(obj), tClass) : null;
    }

    public static Map<String, Object> toMap(String value) {
        return DzStringUtil.isNotBlank(value) ? toMap(value, () -> null) : null;
    }

    public static Map<String, Object> toMap(Object value) {
        return value != null ? toMap(value, () -> null) : null;
    }

    public static Map<String, Object> toMap(Object value, Supplier<Map<String, Object>> defaultSupplier) {
        if (value == null) {
            return defaultSupplier.get();
        }
        try {
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            }
        } catch (Exception e) {
            log.info("fail to convert" + toJSONString(value), e);
        }
        return toMap(toJSONString(value), defaultSupplier);
    }

    public static Map<String, Object> toMap(String value, Supplier<Map<String, Object>> defaultSupplier) {
        if (DzStringUtil.isBlank(value)) {
            return defaultSupplier.get();
        }
        try {
            return (Map<String, Object>)objectMapper.readValue(value, Map.class);
        } catch (Exception e) {
            log.error(String.format("toMap exception\n%s", value), e);
        }
        return defaultSupplier.get();
    }

    public static List<Object> toList(String value) {
        return DzStringUtil.isNotBlank(value) ? toList(value, null) : null;
    }

    public static <T> List<T> toList(String value, Class<T> tClass) {
        return toList(value, tClass,  () -> null);
    }

    public static <T> List<T> toList(String value, Class<T> tClass, Supplier<List<T>> defaultSupplier) {
       
        try {
            if (DzStringUtil.isBlank(value)) {
                return defaultSupplier.get();
            }
            if (tClass == null) {
            	return (List<T>)objectMapper.readValue(value, List.class);
            }
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, tClass);
            return objectMapper.readValue(value, javaType);
        } catch (Throwable e) {
            log.error(String.format("toJavaObjectList exception \n%s\n%s", value, tClass), e);
        }
        return defaultSupplier.get();
    }

    /////////////////////////////////////////////////////////扩展方法
    public static boolean isJsonObject(String str) {
        boolean isJson = false;
        str = str.trim();
        if (str.startsWith("{") && str.endsWith("}")) {
            isJson = true;
        }
        return isJson;
    }

    public static boolean isJsonArray(String str) {
        boolean isJson = false;
        str = str.trim();
        if (str.startsWith("[") && str.endsWith("]")) {
            isJson = true;
        }
        return isJson;
    }

    public static boolean isJson(String str) {
        boolean isJson = false;
        str = str.trim();
        if (isJsonObject(str) || isJsonArray(str)) {
            isJson = true;
        }
        return isJson;
    }

    public static boolean isEquation(String str) {
        boolean isEquation = false;
        if (str.contains("=")) {
            isEquation = true;
        }
        return isEquation;
    }

    //    /**
    //     * JSON字符串解析成Map，如果字符串不是JSON，则将字符串整个设成key为body的value
    //     * 
    //     * @param str
    //     * @return
    //     */
    //    public static Map<String, Object> parseJson(String str) {
    //        Map<String, Object> map = null;
    //        if (DzStringUtil.isNotBlank(str)) {
    //            str = str.trim();
    //            // if (isJson(str)) {
    //            try {
    //                if (isJson(str)) {
    //                    if (str.startsWith("{")) {
    //                        map = toMap(str);
    //                    } else if (str.startsWith("[")) {
    //                        List<Object> list = toList(str);
    //                        map = new HashMap<String, Object>();
    //                        map.put(BODY_KEY, list);
    //                    }
    //                } else {
    //                    map = new HashMap<String, Object>();
    //                    map.put(BODY_KEY, str);
    //                }
    //            } catch (Exception e) {
    //                map = new HashMap<String, Object>();
    //                map.put(BODY_KEY, str);
    //            }
    //            // URLDecoder.decode(arr[1], GatewayUtil.DEFAULT_ENCODING);
    //            // } else {
    //            // json = new JSONObject();
    //            // json.put("body", str);
    //            // }
    //        }
    //        return map;
    //    }

    /**
     * 字符串解析成Map，如果字符串不是等式，则将字符串整个设为body "a=1&b=text" 解析为 {"a": 1, "b":
     * "text"}
     * 
     * @param str
     * @return
     */
    public static Map<String, Object> parseEquation(String str) {
        Map<String, Object> map = null;
        if (DzStringUtil.isNotBlank(str)) {
            if (isEquation(str)) {
                Map<String, Object> emap = Arrays.stream(str.split("&")).map(s -> s.split("=")).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
                map = new HashMap<String, Object>(emap);
                // URLDecoder.decode(arr[1], GatewayUtil.DEFAULT_ENCODING);
            } else {
                map = new HashMap<String, Object>();
                map.put(BODY_KEY, str);
            }
        }
        return map;
    }

    /**
     * 将JSON字符串，解析为java bean
     * 
     * @param str
     * @return
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    public static <T> T parse(String str, Class<T> clazz) {
        return toJavaObject(str, clazz);
    }

    /**
     * 将等式字符串或JSON字符串，解析为JSONObject，如果不是等式或JSON，则将字符串整个设为body <br/>
     * 例1："a=3322&b=中文" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
     * 例2："{'a':3322,'b':'中文'}" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
     * 例3："abcd" 解析为 {"body":"abcd"} 的JSON对象<br/>
     * 
     * @param str
     * @return
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    public static Map<String, Object> parse(String str) {
        if (isJsonObject(str)) {
            return toMap(str);
        } else if (isEquation(str)) {
            return parseEquation(str);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(BODY_KEY, str);
            return map;
        }
    }

    /**
     * 编译JSON Path，获得更好性能
     *
     * @param path
     * @return
     */
    public static JsonPath compile(String path) {
        return JsonPath.compile(path);
    }

    /**
     * 根据JSON Path获取JSON的值
     * 
     * @param root
     * @param path
     * @return
     */
    public static <T> T getValue(String root, String path) {
        return JsonPath.using(jsonPathAsValueConfig).parse(root).read(path);
        //        try {
        //            return JSONPath.eval((JSONObject)root, path);
        //        } catch (Exception e) {
        //            throw new IllegalStateException(e);
        //        }
    }

    /**
     * 根据JSON Path获取JSON的值
     * 
     * @param root
     * @param path
     * @return
     */
    public static <T> T getValue(Object root, String path) {
        return JsonPath.using(jsonPathAsValueConfig).parse(root).read(path);
        //        try {
        //            return JSONPath.eval((JSONObject)root, path);
        //        } catch (Exception e) {
        //            throw new IllegalStateException(e);
        //        }
    }

    /**
     * 根据JSON Path获取JSON的值
     * 
     * @param root
     * @param path
     * @return
     */
    public static <T> T getValue(String root, JsonPath path) {
        return path.read(root, jsonPathAsValueConfig);
    }

    /**
     * 根据JSON Path获取JSON的值
     * 
     * @param root
     * @param path
     * @return
     */
    public static <T> T getValue(Object root, JsonPath path) {
        return path.read(root, jsonPathAsValueConfig);
    }

    /**
     * 根据JSON Path设置JSON的值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static String setValue(String root, String path, Object obj) {
        String json = JsonPath.using(jsonPathAsValueConfig).parse(root).set(path, obj).jsonString();
        return json;
    }

    /**
     * 根据JSON Path设置JSON的值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static <T> T setValue(T root, String path, Object obj) {
        Class tClass = root.getClass();
        String json = JsonPath.using(jsonPathAsValueConfig).parse(root).set(path, obj).jsonString();
        return (T) toJavaObject(json, tClass);
    }

    /**
     * 根据JSON Path设置JSON的值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static <T> T setValue(T root, JsonPath path, Object obj) {
        return path.set(root, obj, jsonPathAsValueConfig);
        //        try {
        //            return JSONPath.set(root, path, obj);
        //        } catch (Exception e) {
        //            throw new IllegalStateException(e);
        //        }
    }

    /**
     * 根据JSON Path添加JSON的值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static String addValue(String root, String path, Object obj) {
        String json = JsonPath.using(jsonPathAsValueConfig).parse(root).add(path, obj).jsonString();
        return json;
    }

    /**
     * 根据JSON Path添加JSON的值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static <T> T addValue(T root, String path, Object obj) {
        Class tClass = root.getClass();
        String json = JsonPath.using(jsonPathAsValueConfig).parse(root).add(path, obj).jsonString();
        return (T) toJavaObject(json, tClass);
    }

    /**
     * 根据JSON Path添加JSON的值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static <T> T addValue(T root, JsonPath path, Object obj) {
        return path.add(root, obj, jsonPathAsValueConfig);
    }

    /**
     * 根据JSON Path删除JSON的键和值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static String delValue(String root, String path, Object obj) {
        String json = JsonPath.using(jsonPathAsValueConfig).parse(root).delete(path).jsonString();
        return json;
    }

    /**
     * 根据JSON Path删除JSON的键和值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static <T> T delValue(T root, String path, Object obj) {
        Class tClass = root.getClass();
        String json = JsonPath.using(jsonPathAsValueConfig).parse(root).delete(path).jsonString();
        return (T) toJavaObject(json, tClass);
    }

    /**
     * 根据JSON Path删除JSON的键和值
     * 
     * @param root
     * @param path
     * @param obj
     * @return
     */
    public static <T> T delValue(T root, JsonPath path, Object obj) {
        return path.delete(root, jsonPathAsValueConfig);
    }

    //
    //	public static boolean isJson(String str) {
    //		boolean isJson = false;
    //		if (str.startsWith("{") && str.endsWith("}") || str.startsWith("[") && str.endsWith("]")) {
    //			Object object = JSON.parse(str);
    //			if (object instanceof com.alibaba.fastjson.JSONObject || object instanceof JSONArray) {
    //				isJson = true;
    //			}
    //		}
    //		return isJson;
    //	}
    //
    //	public static boolean isEquation(String str) {
    //		boolean isEquation = false;
    //		if (str.contains("=")) {
    //			isEquation = true;
    //		}
    //		return isEquation;
    //	}
    //
    //	/**
    //	 * 将等式字符串或JSON字符串，解析为JSONObject，如果不是等式或JSON，则将字符串整个设为"body"的value <br/>
    //	 * 例1："a=3322&b=中文" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例2："{'a':3322,'b':'中文'}" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例3："abcd" 解析为 {"body":"abcd"} 的JSON对象<br/>
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static JSONObject parse(String str) {
    //		JSONObject json = null;
    //		if (StringUtil.isNotBlank(str)) {
    //			if (isJson(str)) {
    //				json = parseJson(str);
    //			} else if (isEquation(str)) {
    //				json = parseEquation(str);
    //			} else {
    //				json = new JSONObject();
    //				json.put(BODY_KEY, str);
    //			}
    //		}
    //		return json;
    //	}
    //
    //	/**
    //	 * 将等式字符串或JSON字符串，解析为JSONObject，如果不是等式或JSON，则将字符串整个设为"body"的value <br/>
    //	 * 例1："a=3322&b=中文" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例2："{'a':3322,'b':'中文'}" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例3："abcd" 解析为 {"body":"abcd"} 的JSON对象<br/>
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static <T> T parse(String str, Class<T> clazz) {
    //		T t = null;
    //		if (StringUtil.isNotBlank(str)) {
    //			if (isJson(str)) {
    //				t = JSON.parseObject(str, clazz);
    //			}
    //		}
    //		return t;
    //	}
    //
    //	/**
    //	 * 将等式字符串或JSON字符串，解析为JSONObject，如果不是等式或JSON，则将字符串整个设为body <br/>
    //	 * 例1："a=3322&b=中文" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例2："{'a':3322,'b':'中文'}" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例3："abcd" 解析为 {"body":"abcd"} 的JSON对象<br/>
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static JSONObject parse(Object obj) {
    //		JSONObject json = null;
    //		if (obj != null) {
    //			if (obj instanceof JSONObject) {
    //				json = (JSONObject) obj;
    //			} else {
    //				String str = toJSONString(obj);
    //				json = parse(str);
    //			}
    //		}
    //		return json;
    //	}
    //
    //	/**
    //	 * 将等式字符串或JSON字符串，解析为JSONObject，如果不是等式或JSON，则将字符串整个设为body <br/>
    //	 * 例1："a=3322&b=中文" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例2："{'a':3322,'b':'中文'}" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例3："abcd" 解析为 {"body":"abcd"} 的JSON对象<br/>
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static <T> T parse(Object obj, Class<T> t) {
    //		T clazzObj = null;
    //		if (obj != null) {
    //				String str = toJSONString(obj);
    //				clazzObj = parse(str, t);
    //		}
    //		return clazzObj;
    //	}
    //
    //	/**
    //	 * 字符串解析成JSONObject，如果字符串不是等式，则将字符串整个设为body "a=1&b=text" 解析为 {"a": 1, "b":
    //	 * "text"}
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static JSONObject parseEquation(String str) {
    //		JSONObject json = null;
    //		if (StringUtil.isNotBlank(str)) {
    //			if (isEquation(str)) {
    //				Map<String, Object> map = Arrays.stream(str.split("&")).map(s -> s.split("="))
    //						.collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    //				json = new JSONObject(map);
    //				// URLDecoder.decode(arr[1], GatewayUtil.DEFAULT_ENCODING);
    //			} else {
    //				json = new JSONObject();
    //				json.put(BODY_KEY, str);
    //			}
    //		}
    //		return json;
    //	}
    //
    //	/**
    //	 * JSONObject反向生成等式<br/>如： {"a": 1, "b": "text"} 生成为 "a=1&b=text"
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static String deparseEquation(JSONObject map) {
    //		String body = null;
    //		if (map != null && map.size() > 0) {
    //			body = map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
    //		}
    //		return body;
    //	}
    //
    //	/**
    //	 * 字符串解析成JSON，如果字符串不是JSON，则将字符串整个设为body
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static JSONObject parseJson(String str) {
    //		JSONObject json = null;
    //		if (StringUtil.isNotBlank(str)) {
    //			str = str.trim();
    //			// if (isJson(str)) {
    //			try {
    //				if (str.startsWith("{")) {
    //					json = JSONObject.parseObject(str, new TypeReference<JSONObject>() {
    //					});
    //				} else if (str.startsWith("[")) {
    //					List<JSONObject> jsonList = JSONObject.parseArray(str, JSONObject.class);
    //					json = new JSONObject();
    //					json.put(BODY_KEY, jsonList);
    //				}
    //			} catch (Exception e) {
    //				json = new JSONObject();
    //				json.put(BODY_KEY, str);
    //			}
    //			// URLDecoder.decode(arr[1], GatewayUtil.DEFAULT_ENCODING);
    //			// } else {
    //			// json = new JSONObject();
    //			// json.put("body", str);
    //			// }
    //		}
    //		return json;
    //	}
    //
    //	/**
    //	 * 将等式字符串或JSON字符串，解析为JSONObject，如果不是等式或JSON，则将字符串整个设为body <br/>
    //	 * 例1："a=3322&b=中文" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例2："{'a':3322,'b':'中文'}" 解析为 {"a":3322,"b":"中文"} 的JSON对象<br/>
    //	 * 例3："abcd" 解析为 {"body":"abcd"} 的JSON对象<br/>
    //	 * 
    //	 * @param str
    //	 * @return
    //	 */
    //	public static <T> List<T> parseArray(String str, Class<T> clazz) {
    //		List<T> list = null;
    //		if (str != null) {
    //			list = JSON.parseArray(str, clazz);
    //		}
    //		return list;
    //	}
    //
    //	/**
    //	 * JSONObject反向生成JSON字符串
    //	 * @param json
    //	 * @return
    //	 */
    //	public static String deparseJson(JSONObject json) {
    //		String body = null;
    //		if (json != null) {
    //			body = json.toJSONString();
    //		}
    //		return body;
    //	}
    //	
    //	/**
    //     * 对象生成JSON字符串
    //     * @param json
    //     * @return
    //     */
    //    public static String toJSONString(Object obj) {
    //        return JSON.toJSONString(obj);
    //    }

    //	/**
    //	 * 编译JSON Path，获得更好性能
    //	 * 
    //	 * @param root
    //	 * @param path
    //	 * @return
    //	 */
    //	public static JSONPath compile(String path) {
    //		try {
    //			return JSONPath.compile(path);
    //		} catch (Exception e) {
    //			throw new IllegalStateException(e);
    //		}
    //	}
    //
    //	/**
    //	 * 根据JSON Path获取JSON的值
    //	 * 
    //	 * @param root
    //	 * @param path
    //	 * @return
    //	 */
    //	public static Object getValue(JSONObject root, JSONPath path) {
    //		try {
    //			return path.eval(root);
    //		} catch (Exception e) {
    //			throw new IllegalStateException(e);
    //		}
    //	}
    //	
    //	/**
    //	 * 根据JSON Path获取JSON的值
    //	 * 
    //	 * @param root
    //	 * @param path
    //	 * @return
    //	 */
    //	public static Object getValue(JSONObject root, String path) {
    //		try {
    //			return JSONPath.eval(root, path);
    //		} catch (Exception e) {
    //			throw new IllegalStateException(e);
    //		}
    //	}
    //	
    //	/**
    //	 * 根据JSON Path设置JSON的值
    //	 * 
    //	 * @param root
    //	 * @param path
    //	 * @param obj
    //	 * @return
    //	 */
    //	public static boolean setValue(JSONObject root, JSONPath path, Object obj) {
    //		try {
    //			return path.set(root, obj);
    //		} catch (Exception e) {
    //			throw new IllegalStateException(e);
    //		}
    //	}
    //
    //	/**
    //	 * 根据JSON Path设置JSON的值
    //	 * 
    //	 * @param root
    //	 * @param path
    //	 * @param obj
    //	 * @return
    //	 */
    //	public static boolean setValue(JSONObject root, String path, Object obj) {
    //		try {
    //			return JSONPath.set(root, path, obj);
    //		} catch (Exception e) {
    //			throw new IllegalStateException(e);
    //		}
    //	}
    public static String putValue(String root, String path, Object value) {
        int index = path.lastIndexOf(".");
        String tmpPath = path.substring(0, index);
        String key = path.substring(index + 1);

        Object getValue = getValue(root, tmpPath);

        if (getValue == null || getValue instanceof List && (((List) getValue).isEmpty()) || ((List) getValue).get(0) == null) {
            root = putValue(root, tmpPath, new HashMap());
        }

        return JsonPath.using(jsonPathAsValueConfig).parse(root).put(tmpPath, key, value).jsonString();
    }

    public static <T> T putValue(T root, String path, Object value) {
        T json = null;

        Class tClass = root.getClass();

        int index = path.lastIndexOf(".");
        if (index > 0) {
            String tmpPath = path.substring(0, index);
            String key = path.substring(index + 1);
            Object getValue = getValue(root, tmpPath);

            if (getValue == null || getValue instanceof List && (((List) getValue).isEmpty()) || ((List) getValue).get(0) == null) {
                root = putValue(root, tmpPath, new HashMap());
            }

            String jsonString = JsonPath.using(jsonPathAsValueConfig).parse(root).put(tmpPath, key, value).jsonString();
            json = (T) toJavaObject(jsonString, tClass);
        } else {
            json = setValue(root, path, value);
        }

        return json;
    }
    

    /**
     * jsonGenerator
     *  [
     *      {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null},
     *      {"address":"address2","name":"name2","id":2,"email":"email2","birthday":null}
     *  ]
     *  ObjectMapper
     *  直接返回list转换成的json[
     *      {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null},
     *      {"address":"address2","name":"name2","id":2,"email":"email2","birthday":null}
     *  ]
     *  [
     *      {"address":"china-Guangzhou","name":"zhaojd","id":1,"email":"mr_zhaojd@163.com","birthday":null},
     *      {"address":"address2","name":"name2","id":2,"email":"email2","birthday":null}
     *  ]
     */

//    /**
//     * 1.4 下面来看看jackson提供的一些类型，用这些类型完成json转换；如果你使用这些类型转换JSON的话，
//     *     那么你即使没有JavaBean(Entity)也可以完成复杂的Java类型的JSON转换。
//     *     下面用到这些类型构建一个复杂的Java对象，并完成JSON转换。
//     */
//    public void othersToJson(Object obj) throws Exception {
//        try {
//            String[] arr = {"a", "b", "c"};
//            System.out.println("jsonGenerator");
//            String str = "hello world jackson!";
//            //byte
//            jsonGenerator.writeBinary(str.getBytes());
//            //boolean
//            jsonGenerator.writeBoolean(true);
//            //null
//            jsonGenerator.writeNull();
//            //float
//            jsonGenerator.writeNumber(2.2f);
//            //char
//            jsonGenerator.writeRaw("c");
//            //String
//            jsonGenerator.writeRaw(str, 5, 10);
//            //String
//            jsonGenerator.writeRawValue(str, 5, 5);
//            //String
//            jsonGenerator.writeString(str);
//            jsonGenerator.writeTree(JsonNodeFactory.instance.POJONode(str));
//            System.out.println();
//
//            //Object
//            jsonGenerator.writeStartObject();//{
//            jsonGenerator.writeObjectFieldStart("user");//user:{
//            jsonGenerator.writeStringField("name", "jackson");//name:jackson
//            jsonGenerator.writeBooleanField("sex", true);//sex:true
//            jsonGenerator.writeNumberField("age", 22);//age:22
//            jsonGenerator.writeEndObject();//}
//
//            jsonGenerator.writeArrayFieldStart("infos");//infos:[
//            jsonGenerator.writeNumber(22);//22
//            jsonGenerator.writeString("this is array");//this is array
//            jsonGenerator.writeEndArray();//]
//
//            jsonGenerator.writeEndObject();//}
//
//            //complex Object
//            jsonGenerator.writeStartObject();//{
//            jsonGenerator.writeObjectField("user", bean);//user:{bean}
//            jsonGenerator.writeObjectField("infos", arr);//infos:[array]
//            jsonGenerator.writeEndObject();//}
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     *   list的长度：2
     *   address:address2
     *   name:zhaojd
     *   id:2
     *   email:email2
     *   address:address
     *   name:zhaojd
     *   id:1
     *   email:email
     */
    /**
     * 2.3 json字符串转换成Array
     */
    public <T> T[] toArray(String jsonStr, Class<T[]> clazz) {
        //String json =
        //        "[{\"address\": \"address2\",\"name\":\"zhaojd2\",\"id\":2,\"email\":\"email2\"},"
        //                + "{\"address\":\"address\",\"name\":\"zhaojd\",\"id\":1,\"email\":\"email\"}]";
    	T[] arr = null;
        try {
            arr = objectMapper.readValue(jsonStr, clazz);
            System.out.println("数组的长度为：" + arr.length);
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return arr;
    }

    /*
     *   map集合的长度为：3
     *   success:true
     *   A:{address=address2, name=zhaojd2, id=2, email=email2}
     *   B:{address=address, name=zhaojd, id=1, email=email}
     */
    /**
     * 3. jackson对xml的支持
     */
    public String toXml(Object bean) throws Exception {
        System.out.println("XmlMapper");
        
        String xmlStr = null;
        try {
            /**
             * javaBean转换成xml
             */
            xmlStr = xmlMapper.writeValueAsString(bean);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } 
        return xmlStr;
    }
}
