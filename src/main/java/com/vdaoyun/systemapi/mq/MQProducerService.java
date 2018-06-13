package com.vdaoyun.systemapi.mq;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

public class MQProducerService {
	
	public static Producer producer = null;
	
	private SendCallback sendCallback = null;
	private String acessKey = null;
	private String secretKey = null;
	private String producerId = null;
	private String gloadTargetID = null;
	private String rootTopicId = null;
	
	public void setRootTopicId(String rootTopicId) {
		this.rootTopicId = rootTopicId;
	}
	
	public void setGloadTargetID(String gloadTargetID) {
		this.gloadTargetID = gloadTargetID;
	}
	
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
	
	/**
	 * 初始化相關配置
	 */
	public void init() {
        Properties properties =new Properties();
        properties.put(PropertyKeyConst.ProducerId, this.producerId);
        properties.put(PropertyKeyConst.AccessKey, this.acessKey);
        properties.put(PropertyKeyConst.SecretKey, this.secretKey);
        producer = ONSFactory.createProducer(properties);
        start();
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
	 * @param topic			  二级主题
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
	public SendResult send(String topic, byte[] body) {
		if (StringUtils.isEmpty(topic)) {
			throw new NullPointerException("topic not null");
		}
		Message msg = new Message(rootTopicId, "MQ2MQTT",  body);
        msg.putUserProperties("mqttSecondTopic", topic);
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
	
	public SendResult sendToClient(String targetClientID, byte[] body) {
		if (StringUtils.isEmpty(targetClientID)) {
			throw new NullPointerException("targetClientID not null");
		}
		Message msg = new Message(rootTopicId, "MQ2MQTT",  body);
        msg.putUserProperties("mqttSecondTopic",  "/p2p/" + gloadTargetID + "" +  targetClientID);
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
	 * Bean销毁时触发
	 */
	public void destroy() {
		producer.shutdown();
		producer = null;
	}
}


