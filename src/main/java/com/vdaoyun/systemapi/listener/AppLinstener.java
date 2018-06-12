package com.vdaoyun.systemapi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vdaoyun.systemapi.web.mqtt.ConsumerService;

@WebListener
public class AppLinstener implements ServletContextListener {
	
	private static final Logger log = LoggerFactory.getLogger(AppLinstener.class);
	
	@Autowired
	private ConsumerService consumerService;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("==================="
				+ "contextInitialized"
				+ "===================");
		// 启动MQTT监听服务
		consumerService.start();
        System.out.println("[Case Normal Consumer Init]   Ok");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("==================="
				+ "contextDestroyed"
				+ "===================");
		
	}

}
