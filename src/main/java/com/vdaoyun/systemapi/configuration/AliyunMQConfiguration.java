package com.vdaoyun.systemapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vdaoyun.systemapi.listener.MQMessageListener;
import com.vdaoyun.systemapi.listener.MQSendCallback;
import com.vdaoyun.systemapi.web.mqtt.ConsumerService;
import com.vdaoyun.systemapi.web.mqtt.ProducerService;

@Configuration
public class AliyunMQConfiguration {
	
	@Autowired
	private MQConfig mqConfig;
	
	@Bean
	public MQMessageListener messageListener() {
		return new MQMessageListener();
	}
	
	@Bean
	public MQSendCallback mqSendCallback() {
		return new MQSendCallback();
	}
	
	/**
	 * mq 消息接收器
	 * @return
	 */
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public ConsumerService consumerService() {
		ConsumerService consumerService = new ConsumerService();
		consumerService.addMessageListener(messageListener());
		consumerService.setAcessKey(mqConfig.getAcessKey());
		consumerService.setConsumerID(mqConfig.getConsumerID());
		consumerService.setSecretKey(mqConfig.getSecretKey());
		return consumerService;
	}
	
	/**
	 * mqtt消息發送器
	 * @return
	 */
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public ProducerService producerService() {
		ProducerService producerService = new ProducerService();
		producerService.setupSendCallback(mqSendCallback());
		producerService.setAcessKey(mqConfig.getAcessKey());
		producerService.setProducerId(mqConfig.getProducerId());
		producerService.setSecretKey(mqConfig.getSecretKey());
		return producerService;
	}

}
