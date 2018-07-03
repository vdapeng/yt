package com.vdaoyun.systemapi.common.utils;

import org.springframework.core.env.Environment;

public final class EnvironmentUtil {
	
	private static Environment environment;
	
	private static final String STRING_SPRING_CLOUD_CONFIG_PROFILE = "spring.cloud.config.profile";
	public static final String DEV = "dev";
	
	public static void addEnvironment(Environment env) {
		environment = env;
	}
	
	public static String getProperty(String key) {
		return environment.getProperty(key);
	}
	
	/**
	 * 
	 * @Title: isDev
	 *  
	 * @Description: 是否为开发模式
	 *  
	 * @return Boolean
	 */
	public static Boolean isDev() {
		return getProperty(STRING_SPRING_CLOUD_CONFIG_PROFILE).toLowerCase().equals(DEV);
	}

}
