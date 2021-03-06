package com.vdaoyun.systemapi.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @Package com.vdaoyun.systemapi.configuration
 *  
 * @ClassName: SpringContextHolder
 *  
 * @Description: 全局ApplicationContext，用于获取Bean
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月3日 下午3:34:46
 *
 */
@SuppressWarnings("unchecked")
@Configuration
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}
	
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

	public static void cleanApplicationContext() {
		applicationContext = null;
	}

}
