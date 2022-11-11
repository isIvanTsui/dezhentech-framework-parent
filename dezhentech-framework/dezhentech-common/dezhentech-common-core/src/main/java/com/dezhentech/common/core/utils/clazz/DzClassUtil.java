package com.dezhentech.common.core.utils.clazz;

import com.dezhentech.common.core.exceptions.ServiceException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * dz类工具
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.clazz.DzClassUtil
 * @since 2022/11/10 15:57:18
 **/
public class DzClassUtil extends cn.hutool.core.util.ClassUtil {

    /**
     * 动态编译
     *
     * @param filePath 文件路径
     * @param args     arg游戏
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 15:59:12
     */
    public static void compile(String filePath, String... args) {

        //目标文件夹
        String targetDir = DzClassUtil.getClassPath();//System.getProperty("user.dir") + "/target/classes";
        System.out.println(targetDir);

        List<String> compileArgList = new ArrayList<String>();

        compileArgList.add("-d");
        compileArgList.add(targetDir);
        for (String arg : args) {
            compileArgList.add(arg);
        }

        compileArgList.add(filePath);

        String[] compileArgs = compileArgList.toArray(new String[compileArgList.size()]);

        //动态编译
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        int status = javac.run(null, null, null, compileArgs);
        if (status != 0) {
            throw new ServiceException("没有编译成功！");
        }

    }

    /**
     * 执行
     *
     * @param className  类名
     * @param methodName 方法名称
     * @param args       参数
     * @return {@code Object }
     * @author xu.zhang@dezhentech.com
     * @since 2022/11/10 15:59:49
     */
    public static Object exec(String className, String methodName, Object... args) {
        try {
            //动态执行
            //Class clz = Class.forName("AlTest");//返回与带有给定字符串名的类 或接口相关联的 Class 对象。

            Class clz = loadClass(className);
            Object o = clz.newInstance();

            Class[] argsClasses = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                argsClasses[i] = arg.getClass();
                if (ArrayList.class.equals(argsClasses[i])) {
                    argsClasses[i] = List.class;
                }
                if (HashMap.class.equals(argsClasses[i]) || LinkedHashMap.class.equals(argsClasses[i]) || ConcurrentHashMap.class.equals(argsClasses[i])) {
                    argsClasses[i] = Map.class;
                }
            }

            //返回一个 Method 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明方法
            Method method = clz.getDeclaredMethod(methodName, argsClasses);
            //静态方法第一个参数可为null,第二个参数为实际传参
            Object result = method.invoke(o, args);

            return result;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
