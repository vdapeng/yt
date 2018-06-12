package com.vdaoyun.systemapi.web.mqtt;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

public class ProducerService {
	
	public static Producer producer = null;
	
	private SendCallback sendCallback = null;
	private String acessKey = null;
	private String secretKey = null;
	private String producerId = null;
	private String rootTopic = null;
	
	public void setupSendCallback(SendCallback sendCallback) {
		this.sendCallback = sendCallback;
	}
	
	public void setAcessKey(String acessKey) {
		this.acessKey = acessKey;
	}
	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	
	public void setRootTopic(String rootTopic) {
		this.rootTopic = rootTopic;
	}
	
	/**
	 * 初始化相關配置
	 */
	public void init() {
        Properties properties =new Properties();
        properties.put(PropertyKeyConst.ProducerId, this.producerId);
        properties.put(PropertyKeyConst.AccessKey, this.acessKey);
        properties.put(PropertyKeyConst.SecretKey, this.secretKey);
        producer = ONSFactory.createProducer(properties);
	}
	
	/**
	 * 啟動服務
	 */
	public void start() {
		producer.start();
	}
	
	/**
	 * 
	 * 發送消息
	 * 
	 * @param topic			  主題，如不填，默認使用配置文件中的rootTopic
	 * @param targetClientID  接收人編號	 "GID_HJKJ0001@@@DeviceID_0001";
	 * @param msg			      發送的消息
	 * 
	 * 		example:
	 * 			final byte[] body=new byte[1024];
		        msg = new Message(
	                topic,//MQ 消息的 Topic，需要事先创建
	                "MQ2MQTT",//MQ Tag，通过 MQ 向 MQTT 客户端发消息时，必须指定 MQ2MQTT 作为 Tag，其他 Tag 或者不设都将导致 MQTT 客户端收不到消息
	                body);//消息体，和 MQTT 的 body 对应		
	 * 
	 * @return
	 */
	public SendResult send(String topic, String targetClientID, Message msg) {
        if (StringUtils.isEmpty(topic)) {
        	topic = this.rootTopic;
		}
		if (StringUtils.isEmpty(targetClientID)) {
			throw new NullPointerException("targetClientID not null");
		}
        msg.putUserProperties("mqttSecondTopic", targetClientID);
        if (producer.isClosed()) {
			producer.start();
		}
        if (sendCallback != null) {
			producer.sendAsync(msg, sendCallback);
			return null;
		} else {
			//发送消息，只要不抛异常就是成功。
	        SendResult sendResult = producer.send(msg);
	        return sendResult;
		}
	}
	
	/**
	 * 銷毀時觸發
	 */
	public void destroy() {
		producer.shutdown();
		producer = null;
	}
	
}


