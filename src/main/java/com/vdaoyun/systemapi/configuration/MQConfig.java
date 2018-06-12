package com.vdaoyun.systemapi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mqtt")
public class MQConfig {
	
	private String acessKey;
	
	private String secretKey;
	
	private String consumerID;
	
	private String producerId;

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
	
	

}
