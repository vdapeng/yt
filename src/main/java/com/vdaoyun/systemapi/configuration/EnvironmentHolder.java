package com.vdaoyun.systemapi.configuration;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 
 * @Package com.vdaoyun.systemapi.configuration
 *  
 * @ClassName: EnvironmentHolder
 *  
 * @Description: 全局Environment，用于获取配置文件属性值
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月3日 下午3:37:04
 *
 */
@Configuration
public class EnvironmentHolder implements EnvironmentAware {
	
	private static Environment environment;
		
	// 默认值
	private static final String defaultValue = "";

	@Override
	public void setEnvironment(Environment environment) {
		EnvironmentHolder.environment = environment;
	}
	
	public static <T> T getProperty(String key ,Class<T> clazz) {
		checkEnvironment();
		return (T) environment.getProperty(key, clazz);
	}
	
	public static String getProperty(String key) {
		checkEnvironment();
		return environment.getProperty(key, defaultValue);
	}
	
	private static void checkEnvironment() {
		if (environment == null) {
			throw new IllegalStateException("environment未注入,请在applicationContext.xml中定义Environment");
		}
	}
	
	public static void cleanEnvironment() {
		environment = null;
	}
	
	public static Environment getEnvironment() {
		checkEnvironment();
		return environment;
	}

}
