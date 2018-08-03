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
		checkEnv();
		return environment.getProperty(key);
	}
	
	public static void checkEnv() {
		if (environment == null) {
			throw new IllegalStateException("environment未注入,请在SystemApiApplication中注入Environment");
		}
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
		checkEnv();
		return getProperty(STRING_SPRING_CLOUD_CONFIG_PROFILE).equalsIgnoreCase(DEV);
	}

}
