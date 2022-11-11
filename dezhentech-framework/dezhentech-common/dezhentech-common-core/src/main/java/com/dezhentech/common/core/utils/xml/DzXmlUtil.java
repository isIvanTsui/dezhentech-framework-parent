package com.dezhentech.common.core.utils.xml;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.XmlUtil;
import com.dezhentech.common.core.exceptions.ServiceException;
import com.dezhentech.common.core.utils.clazz.DzReflectUtil;
import com.dezhentech.common.core.utils.clazz.DzTypeUtil;
import com.dezhentech.common.core.utils.objects.DzListUtil;
import com.dezhentech.common.core.utils.objects.DzStringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import org.dom4j.*;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.*;


/**
 * dzxml工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.xml.DzXmlUtil
 * @since 2022/11/10 20:44:53
 **/
public class DzXmlUtil extends XmlUtil {

    private static Map<Class, XStream> xstreamMap = new HashMap<>();
    private static final String BASE_PACKAGE = "com.dezhentech";

    /**
     * @param param 数据内容
     * @param paths 表达式
     * @return {@code Map<String, Object> }
     * @throws Exception 异常
     * @descriptions 解析
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 09:19:46
     */
    public static Map<String, Object> parse(String param, Map<String, String> paths) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        XPathFactory xpathFactory = XPathFactory.newInstance();

        InputSource inputSource = new InputSource(new StringReader(param));
        org.w3c.dom.Document document = dbFactory.newDocumentBuilder().parse(inputSource);
        Map<String, Object> map = new HashMap<>();
        for (String key : paths.keySet()) {
            XPath xpath = xpathFactory.newXPath();
            Node node = (Node) xpath.evaluate(paths.get(key), document, XPathConstants.NODE);
            if (node == null) {
                throw new Exception("node not found, xpath is " + paths.get(key));
            }
            map.put(key, node.getTextContent());
        }
        return map;
    }

    public static <T> XStream getXStream(Class<T> clazz) {
        XStream xstream = xstreamMap.get(clazz);
        if (xstream == null) {
            xstream = createXStream(clazz);
            xstreamMap.put(clazz, xstream);
        }
        return xstream;
    }

    public static <T> XStream createXStream(Class<T> clazz) {
        XStream xstream = new XStream();
        //Security framework
        XStream.setupDefaultSecurity(xstream);
//		xstream.allowTypesByWildcard(new String[] {  "com.dezhentech.**" });
        xstream.allowTypesByRegExp(new String[]{".*"});

        //xstream.autodetectAnnotations(true);
        xstream.ignoreUnknownElements();

        String name = AnnotationUtil.getAnnotationValue(clazz, XmlType.class, "name");

        registerClass(name, clazz, xstream);


        return xstream;
    }

    /**
     * @param name    名字
     * @param clazz 类
     * @param xstream xstream
     * @descriptions 注册类名<br />
     * @author xu.zhang@dezhentech.com
     * @time 2022/11/10 09:19:56
     */
    public static <T> void registerClass(String name, Class<T> clazz, XStream xstream) {
        if (!clazz.getName().startsWith(BASE_PACKAGE)) {
            return;
        }

        ClassAliasingMapper mapper = (ClassAliasingMapper) xstream.getMapper()
                .lookupMapperOfType(ClassAliasingMapper.class);
        if (mapper.itemTypeAsAttribute(clazz)) {
            return;
        }
        if (DzStringUtil.isNotBlank(name)) {
            xstream.alias(name, clazz);
        } else {
            xstream.alias(DzStringUtil.lowerFirst(clazz.getSimpleName()), clazz);
        }
        //xstream.processAnnotations(clazz);

        //处理成员变量
        Field[] fields = DzReflectUtil.getFields(clazz);
        for (Field field : fields) {
            Class<?> fieldClazz = field.getType();

            String fieldName = field.getName();

            String aliasName = fieldName;

            // 是List的情况下
            if (DzListUtil.isList(fieldClazz)) {

                Class<?> typeClazz = (Class<?>) DzTypeUtil.getTypeArgument(DzTypeUtil.getType(field));

                if (AnnotationUtil.hasAnnotation(field, XmlElementWrapper.class)) {
                    //成员变量作为List属性
                    aliasName = AnnotationUtil.getAnnotationValue(field, XmlElementWrapper.class, "name");
                    if (!"##default".equals(aliasName)) {
                        xstream.aliasField(aliasName, clazz, fieldName);
                    } else {
                        aliasName = fieldName;
                    }

                } else {
                    //隐式集合混叠
                    xstream.addImplicitCollection(clazz, fieldName, fieldName, typeClazz);
                }

                registerClass(fieldName, typeClazz, xstream);

            } else {
                if (AnnotationUtil.hasAnnotation(field, XmlElement.class)) {
                    //成员变量作为XML节点
                    aliasName = AnnotationUtil.getAnnotationValue(field, XmlElement.class, "name");
                    if (!"##default".equals(aliasName)) {
                        xstream.aliasField(aliasName, clazz, fieldName);
                    } else {
                        aliasName = fieldName;
                    }
                } else if (AnnotationUtil.hasAnnotation(field, XmlAttribute.class)) {
                    //成员变量作为XML属性
                    xstream.useAttributeFor(clazz, fieldName);

                    aliasName = AnnotationUtil.getAnnotationValue(field, XmlAttribute.class, "name");
                    if (!"##default".equals(aliasName)) {
                        xstream.aliasAttribute(clazz, fieldName, aliasName);
                    } else {
                        aliasName = fieldName;
                    }
                }

                registerClass(aliasName, fieldClazz, xstream);

            }

        }
    }

    public static <T> T toBean(String xml, Class<T> clazz) {
        XStream xstream = getXStream(clazz);

        T xmlObject = null;
        //        //自定义解析器
        //        //Xstream序列化XML，解析器用StaxDriver
        //        XStream xstream = new XStream(new StaxDriver());
        //        //Xstream序列化Json，解析器用JettisonMappedXmlDriver
        //        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        xmlObject = (T) xstream.fromXML(xml);
        return xmlObject;
    }

    public static String toXmlString(Object obj) {
        //序列化对象到XML
        Class<?> clazz = obj.getClass();
        XStream xstream = getXStream(clazz);

        //		xstream.alias(obj.getClass().getSimpleName(), obj.getClass());
        //		xstream.processAnnotations(obj.getClass());
        //		xstream.autodetectAnnotations(true);
        // Object to XML Conversion

        return xstream.toXML(obj);
    }

    //	/**
    //	 * 格式化xml,显示为容易看的XML格式
    //	 *
    //	 * @param xml 需要格式化的xml字符串
    //	 * @return
    //	 */
    //	public static String format(String xml) throws Exception {
    //		String requestXML = null;
    //
    //		// 拿取解析器
    //		SAXReader reader = new SAXReader();
    //		Document document = reader.read(new StringReader(xml));
    //		if (null != document) {
    //			StringWriter stringWriter = new StringWriter();
    //			// 格式化,每一级前的空格
    //			OutputFormat format = new OutputFormat("    ", true);
    //			// xml声明与内容是否添加空行
    //			format.setNewLineAfterDeclaration(false);
    //			// 是否设置xml声明头部
    //			format.setSuppressDeclaration(false);
    //			// 是否分行
    //			format.setNewlines(true);
    //			XMLWriter writer = new XMLWriter(stringWriter, format);
    //			writer.write(document);
    //			writer.flush();
    //			writer.close();
    //			requestXML = stringWriter.getBuffer().toString();
    //		}
    //		return requestXML;
    //
    //	}
    //

    /**
     * (多层)xml格式字符串转换为map
     *
     * @param xml xml字符串
     * @return 第一个为Root节点，Root节点之后为Root的元素，如果为多层，可以通过key获取下一层Map
     */
    public static Map<String, Object> toMap(String xml) {
        Document doc = null;

        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            throw new ServiceException(e.getMessage());
        }

        Map<String, Object> map = new HashMap<>();
        if (null == doc) {
            return map;
        }
        // 获取根元素
        Element rootElement = doc.getRootElement();
        recursionXmlToMap(rootElement, map);
        return map;
    }

    /**
     * multilayerXmlToMap核心方法，递归调用
     *
     * @param element 节点元素
     * @param outmap  用于存储xml数据的map
     */
    @SuppressWarnings("unchecked")
    private static void recursionXmlToMap(Element element, Map<String, Object> outmap) {
        // 得到根元素下的子元素列表
        List<Element> elelist = element.elements();
        List<Attribute> attrList = element.attributes();

        if (elelist.size() == 0 && attrList.size() == 0) {
            // 如果没有子元素,则将其存储进map中
            outmap.put(element.getName(), element.getTextTrim());
        } else {
            // innermap用于存储子元素的属性名和属性值
            Map<String, Object> innermap = new LinkedHashMap<>();

            if (elelist.size() != 0) {

                List<Map<String, Object>> innerlist = new ArrayList<>();
                // 遍历子元素
                elelist.forEach(childElement -> {
                    if (element.elements(childElement.getName()).size() > 1) {
                        Map<String, Object> innermap1 = new LinkedHashMap<>();
                        recursionXmlToMap(childElement, innermap1);
                        innerlist.add(innermap1);
                        innermap.put(childElement.getName(), innerlist);
                        outmap.put(element.getName(), innermap);
                    } else {
                        recursionXmlToMap(childElement, innermap);

                        outmap.put(element.getName(), innermap);
                    }
                });

            }
            if (attrList.size() != 0) {
                Hashtable<String, String> innertable = new Hashtable<>();
                attrList.forEach(childAttribute -> recursionXmlToTable(childAttribute, innertable));
                outmap.put(element.getName(), innertable);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void recursionXmlToTable(Attribute attribute, Hashtable<String, String> outtable) {
        // 如果没有子元素,则将其存储进map中
        outtable.put(attribute.getName(), attribute.getValue());

    }

    public static String mapToXml(Map<String, Object> map) {
        return mapToXml(map, true, false);
    }

    /**
     * (多层)map转换为xml格式字符串
     *
     * @param map        需要转换为xml的map
     * @param isImplicit 是否隐式集合
     * @param isCdata    是否加入CDATA标识符 true:加入 false:不加入
     * @return xml字符串
     */
    public static String mapToXml(Map<String, Object> map, boolean isImplicit, boolean isCdata) {
        String parentName = "xml";
        Document doc = DocumentHelper.createDocument();
        doc.addElement(parentName);
        String xml = recursionMapToXml(doc.getRootElement(), map, isImplicit, isCdata);
        //return format(xml);
        return xml;
    }

    /**
     * multilayerMapToXml核心方法，递归调用
     *
     * @param element 节点元素
     * @param map     需要转换为xml的map
     * @param isCdata 是否加入CDATA标识符 true:加入 false:不加入
     * @return xml字符串
     */
    @SuppressWarnings("unchecked")
    private static String recursionMapToXml(Element element, Map<String, Object> map, boolean isImplicit,
                                            boolean isCdata) {
        //Element xmlElement = element.addElement(parentName);

        for (String key : map.keySet()) {
            Object obj = map.get(key);
            if (obj instanceof Hashtable) {
                Element xmlElement = element.addElement(key);

                Hashtable<String, String> table = (Hashtable<String, String>) obj;

                table.keySet().forEach(name -> xmlElement.addAttribute(name, table.get(name)));
            } else if (obj instanceof Map) {
                Element xmlElement = element.addElement(key);
                recursionMapToXml(xmlElement, (Map<String, Object>) obj, isImplicit, isCdata);
            } else if (obj instanceof List) {
                Element xmlElement = null;
                if (isImplicit) {
                    xmlElement = element;
                } else {
                    xmlElement = element.addElement(key);
                }
                List<Map> list = (List<Map>) obj;
                for (Map node : list) {
                    recursionMapToXml(xmlElement, node, isImplicit, isCdata);
                }

            } else {
                String value = obj == null ? "" : obj.toString();
                Element xmlElement = element.addElement(key);
                if (isCdata) {
                    xmlElement.addCDATA(value);
                } else {
                    xmlElement.addText(value);
                }
            }
        }

        return element.asXML();
    }
}
