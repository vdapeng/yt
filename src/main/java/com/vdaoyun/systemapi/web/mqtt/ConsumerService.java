package com.vdaoyun.systemapi.web.mqtt;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

public class ConsumerService {

	// 消息监听器
	private MessageListener messageListener = null;
	private String acessKey = null;
	private String secretKey = null;
	private String consumerID = null;
	
	private Consumer consumer = null;
	
	public void addMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
	
	public void setAcessKey(String acessKey) {
		this.acessKey = acessKey;
	}
	
	public void setConsumerID(String consumerID) {
		this.consumerID = consumerID;
	}
	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	// 初始化服务配置
	public void init() {
        Properties properties =new Properties();
        properties.put(PropertyKeyConst.ConsumerId, this.consumerID);
        properties.put(PropertyKeyConst.AccessKey, this.acessKey);
        properties.put(PropertyKeyConst.SecretKey, this.secretKey);
        this.consumer = ONSFactory.createConsumer(properties);
	}
	
	// 启动服务
	public void start() {
		/**
         * 收消息使用的一级 Topic，需要先在 MQ 控制台里创建
         */
        final String topic ="HJKJ_DATA01";
		/**
         * 此处 MQ 客户端只需要订阅 MQTT 的一级 Topic 即可
         */
        this.consumer.subscribe(topic, "*", this.messageListener);
		start(null);
	}
	
	public void start(String topic) {
		if (StringUtils.isNotEmpty(topic)) {
			this.consumer.subscribe(topic, "*", this.messageListener);
		}
		this.consumer.start();
	}
	
	public void destroy() {
		this.consumer.shutdown();
		this.consumer = null;
	}
	
}
