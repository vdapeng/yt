package com.vdaoyun.systemapi.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

public class MQSendCallback implements SendCallback {
	
	private static final Logger log = LoggerFactory.getLogger(MQSendCallback.class);

	@Override
	public void onException(OnExceptionContext arg0) {
		log.error(arg0.getException().getMessage());
	}

	@Override
	public void onSuccess(SendResult arg0) {
		log.info(arg0.toString());
	}

}
