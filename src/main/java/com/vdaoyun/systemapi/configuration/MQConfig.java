package com.vdaoyun.systemapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vdaoyun.systemapi.listener.MQMessageListener;
import com.vdaoyun.systemapi.web.mqtt.ConsumerService;

@Configuration
public class MQConfig {
	
	@Bean
	public MQMessageListener messageListener() {
		return new MQMessageListener();
	}
	
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public ConsumerService consumerService() {
		ConsumerService consumerService = new ConsumerService();
		consumerService.addMessageListener(messageListener());
		consumerService.setAcessKey("LTAIJYtclxVAB3Pd");
		consumerService.setConsumerID("CID_HJKJ0001");
		consumerService.setSecretKey("r0igsclU0CGTg6M2VpCdtCcwW02gYV");
		return consumerService;
	}

}
