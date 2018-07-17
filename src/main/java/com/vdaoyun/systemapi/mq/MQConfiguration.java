package com.vdaoyun.systemapi.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class MQConfiguration {
	
	@Autowired
	private MQProperty mqProperty;
	
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
	public MQConsumerService consumerService() {
		MQConsumerService consumerService = new MQConsumerService();
		consumerService.addMessageListener(messageListener());
		consumerService.setAcessKey(mqProperty.getAcessKey());
		consumerService.setConsumerID(mqProperty.getConsumerID());
		consumerService.setSecretKey(mqProperty.getSecretKey());
		consumerService.setRootTopicId(mqProperty.getRootTopic());
		return consumerService;
	}
	
	/**
	 * mqtt消息發送器
	 * @return
	 */
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public MQProducerService producerService() {
		MQProducerService producerService = new MQProducerService();
		producerService.setupSendCallback(mqSendCallback());
		producerService.setAcessKey(mqProperty.getAcessKey());
		producerService.setProducerId(mqProperty.getProducerId());
		producerService.setSecretKey(mqProperty.getSecretKey());
		producerService.setRootTopicId(mqProperty.getRootTopic());
		producerService.setGloadTargetID(mqProperty.getGloadTargetID());
		return producerService;
	}

}
