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
	 * @Title: 是否为开发模式
	 *  
	 * @Description: 获取当前环境是否为开发环境
	 *  
	 * @return Boolean 是/否
	 */
	public static Boolean isDev() {
		return getProperty(STRING_SPRING_CLOUD_CONFIG_PROFILE).equalsIgnoreCase(DEV);
	}

}
