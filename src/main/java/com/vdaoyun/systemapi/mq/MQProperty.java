package com.vdaoyun.systemapi.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @Package com.vdaoyun.systemapi.mq
 *  
 * @ClassName: MQProperty
 *  
 * @Description: 读取mq相关配置文件
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月13日 上午9:59:21
 *
 */
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MQProperty {
	
	private String acessKey;
	
	private String secretKey;
	
	private String consumerID;
	
	private String producerId;
	
	private String gloadTargetID;
	
	private String rootTopic;
	
	public String getAcessKey() {
		return acessKey;
	}

	public void setAcessKey(String acessKey) {
		this.acessKey = acessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getConsumerID() {
		return consumerID;
	}

	public void setConsumerID(String consumerID) {
		this.consumerID = consumerID;
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getGloadTargetID() {
		return gloadTargetID;
	}

	public void setGloadTargetID(String gloadTargetID) {
		this.gloadTargetID = gloadTargetID;
	}

	public String getRootTopic() {
		return rootTopic;
	}

	public void setRootTopic(String rootTopic) {
		this.rootTopic = rootTopic;
	}
	
	

}
