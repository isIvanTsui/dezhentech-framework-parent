package com.dezhentech.common.core.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Srping ApplicationContext<br/>
 * 如SpringUtil.applicationContext为null，可按以下二种方法处理<br/>
 * ① 启动类得到context并设置到SpringUtil<br/>
 * ApplicationContext  app= SpringApplication.run(MainApplication.class, args);<br/>
 * SpringUtil.setApplicationContext(context);<br/>
 * ② 启动类加注解"@Import({ SpringUtil.class })"<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.spring.DzSpringUtil
 * @since 2022/11/10 20:45:07
 **/
@Component
//@Lazy(false)
@Slf4j
public class DzSpringUtil extends cn.hutool.extra.spring.SpringUtil {

//	private static ApplicationContext applicationContext;// = new GenericApplicationContext();
//
//	public void setApplicationContext(ApplicationContext context) throws BeansException {
//		// if(SpringUtil.applicationContext == null) {
//		SpringUtil.applicationContext = context;
//		// }
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<< SpringUtil >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		log.debug("<<<<<<<<<<<<<<<<<<<< ApplicationContext配置成功 >>>>>>>>>>>>>>>>>>>>>>>");
//		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//	}
//
//	// 获取applicationContext
//	public static ApplicationContext getApplicationContext() {
//		return SpringUtil.applicationContext;
//	}
//
//	// 通过name获取 Bean.
//	public static Object getBean(String name) {
//		return getApplicationContext().getBean(name);
//	}
//
//	// 通过class获取Bean.
//	public static <T> T getBean(Class<T> clazz) {
//		return getApplicationContext().getBean(clazz);
//	}
//
//	// 通过name,以及Clazz返回指定的Bean
//	public static <T> T getBean(String name, Class<T> clazz) {
//		return getApplicationContext().getBean(name, clazz);
//	}
//
//	// 通过Clazz返回指定的Bean
//	public static <T> Map<String, T> getBeans(Class<T> clazz) {
//		return getApplicationContext().getBeansOfType(clazz);
//	}
	
	/**
	 * 通过class获取Bean
	 *
	 * @param <T>   Bean类型
	 * @param clazz Bean类
	 * @return Bean对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getBeanFactory().getBean(clazz);
	}
	
	/**
	 * 获取{@link ListableBeanFactory}，可能为{@link ConfigurableListableBeanFactory} 或 {@link ApplicationContextAware}
	 *
	 * @return {@link ListableBeanFactory}
	 * @since 5.7.0
	 */
	public static ListableBeanFactory getBeanFactory() {
		//return null == beanFactory ? applicationContext : beanFactory;
		ListableBeanFactory factory = cn.hutool.extra.spring.SpringUtil.getBeanFactory();
		if (null ==  factory) {
			factory = getApplicationContext();
			if (null ==  factory) {
				//org.springframework.context.support
				ApplicationContext context =  new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
				factory = context;
				if (null !=  context) {
					cn.hutool.extra.spring.SpringUtil util = context.getBean(cn.hutool.extra.spring.SpringUtil.class);
				
					util.setApplicationContext(context);
					
				}
			}
		} 
		
		return factory;
		
	}

	/**
	 * 主动向Spring容器中注册bean
	 *
	 * @param name  BeanName
	 * @param clazz 注册的bean的类性
	 * @param args  构造方法的必要参数，顺序和类型要求和clazz中定义的一致
	 * @author xu.zhang@dezhentech.com
	 * @since 2022/11/10 20:34:12
	 */
	public static <T> void registerBean(String name, Class<T> clazz, Object... args) {
		ConfigurableApplicationContext applicationContext = ((ConfigurableApplicationContext)getApplicationContext());
		if (applicationContext.containsBean(name)) {
			Object bean = applicationContext.getBean(name);
			if (bean.getClass().isAssignableFrom(clazz)) {
				//return (T) bean;
			} else {
				throw new RuntimeException(String.format("BeanName 重复 [{}]", name));
			}
		}

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		for (Object arg : args) {
			beanDefinitionBuilder.addConstructorArgValue(arg);
		}
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

		BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
		beanFactory.registerBeanDefinition(name, beanDefinition);
		//return applicationContext.getBean(name, clazz);
	}
	
	public static <T> void registerBean(String name, Class<T> clazz, Map<String, Object> fileds) {
		ConfigurableApplicationContext applicationContext = ((ConfigurableApplicationContext)getApplicationContext());
		if (applicationContext.containsBean(name)) {
			Object bean = applicationContext.getBean(name);
			if (bean.getClass().isAssignableFrom(clazz)) {
				//return (T) bean;
			} else {
				throw new RuntimeException(String.format("BeanName 重复 [{}]", name));
			}
		}

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		for (String key : fileds.keySet()) {
			beanDefinitionBuilder.addPropertyValue(key, fileds.get(key));
		}
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

		BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
		beanFactory.registerBeanDefinition(name, beanDefinition);
		//return applicationContext.getBean(name, clazz);
	}

//	public static <T> void registerBean(String name, T obj) {
//		ConfigurableApplicationContext applicationContext = ((ConfigurableApplicationContext)getApplicationContext());
//		if (applicationContext.containsBean(name)) {
//			Object bean = applicationContext.getBean(name);
//			if (bean.getClass().isAssignableFrom(obj.getClass())) {
//				//return (T) bean;
//			} else {
//				throw new RuntimeException(String.format("BeanName 重复 [{}]", name));
//			}
//		}
//		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//		beanFactory.registerSingleton(name, obj);
//		//return applicationContext.getBean(name, (Class<T>)obj.getClass());
//		
//	}

}
