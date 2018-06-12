package com.vdaoyun.systemapi.web.mqtt;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

public class ProducerService {
	
	public static Producer producer = null;
	
	public static SendCallback sendCallback = null;
	
	public void init() {
		/**
         * 设置阿里云的 AccessKey，用于鉴权
         */
        final String acessKey ="LTAIJYtclxVAB3Pd";
        /**
         * 设置阿里云的 SecretKey，用于鉴权
         */
        final String secretKey ="r0igsclU0CGTg6M2VpCdtCcwW02gYV";
        /**
         * ProducerID，需要先在 MQ 控制台里创建
         */
        final String producerId ="PID_HJKJ0001";
        Properties properties =new Properties();
        properties.put(PropertyKeyConst.ProducerId, producerId);
        properties.put(PropertyKeyConst.AccessKey, acessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        producer = ONSFactory.createProducer(properties);
	}
	
	public void start() {
		producer.start();
	}
	
	public SendResult send(String targetClientID, Message msg) {
		/**
         * 发消息使用的一级 Topic，需要先在 MQ 控制台里创建
         */
        final String topic ="HJKJ_DATA01";
        final byte[] body=new byte[1024];
        msg = new Message(
                topic,//MQ 消息的 Topic，需要事先创建
                "MQ2MQTT",//MQ Tag，通过 MQ 向 MQTT 客户端发消息时，必须指定 MQ2MQTT 作为 Tag，其他 Tag 或者不设都将导致 MQTT 客户端收不到消息
                body);//消息体，和 MQTT 的 body 对应
        /**
         * 使用 MQ 客户端给 MQTT 设备发送 P2P 消息时，需要在 MQ 消息中设置 mqttSecondTopic 属性
         * 设置的值是“/p2p/”+目标 ClientID
         */
        targetClientID = "GID_HJKJ0001@@@DeviceID_0001";
        msg.putUserProperties("mqttSecondTopic", "/p2p/"+targetClientID);
        
        if (sendCallback != null) {
			producer.sendAsync(msg, sendCallback);
		}
        
        //发送消息，只要不抛异常就是成功。
        SendResult sendResult = producer.send(msg);
        
        return sendResult;
	}
	
	public void destroy() {
		producer.shutdown();
		producer = null;
	}

}
