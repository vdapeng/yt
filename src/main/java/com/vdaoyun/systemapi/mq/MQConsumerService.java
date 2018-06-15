package com.vdaoyun.systemapi.mq;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.mqtt
 *  
 * @ClassName: ConsumerService
 *  
 * @Description: MQ 消息监听服务
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月13日 上午9:00:35
 *
 */
public class MQConsumerService {

	// 消息监听器
	private MessageListener messageListener = null;
	private String acessKey = null;
	private String secretKey = null;
	private String consumerID = null;
	private Consumer consumer = null;
	private String rootTopicId = null;
	
	public void setRootTopicId(String rootTopicId) {
		this.rootTopicId = rootTopicId;
	}
	
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
	
	/**
	 * 
	 * @Title: init
	 *  
	 * @Description: 构建函数执行完后执行
	 *   void
	 */
	public void init() {
        Properties properties =new Properties();
        properties.put(PropertyKeyConst.ConsumerId, this.consumerID);
        properties.put(PropertyKeyConst.AccessKey, this.acessKey);
        properties.put(PropertyKeyConst.SecretKey, this.secretKey);
        this.consumer = ONSFactory.createConsumer(properties);
        // 启用服务
        start();
	}
	
	public Boolean isStarted() {
		return consumer.isStarted();
	}
	
	public Boolean isClosed() {
		return consumer.isClosed();
	}
	
	// 启动服务
	public void start() {
		/**
         * 此处 MQ 客户端只需要订阅 MQTT 的一级 Topic 即可
         */
        this.consumer.subscribe(rootTopicId, "*", this.messageListener);
        if (consumer.isClosed()) {
        	start(null);
		}
		
	}
	
	public void start(String topic) {
		if (StringUtils.isNotEmpty(topic)) {
			this.consumer.subscribe(topic, "*", this.messageListener);
		}
		if (consumer.isClosed()) {
			this.consumer.start();
		}
	}
	
	public void reStart(String topic) {
		if (StringUtils.isNotEmpty(topic)) {
			this.consumer.subscribe(topic, "*", this.messageListener);
		}
		if (consumer.isStarted()) {
			consumer.shutdown();
		}
		consumer.start();
	}
	
	/**
	 * 
	 * @Title: destroy
	 *  
	 * @Description: Bean 销毁之前执行
	 *   void
	 */
	public void destroy() {
		this.consumer.shutdown();
		this.consumer = null;
	}
	
}
