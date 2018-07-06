package com.vdaoyun.systemapi.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

/**
 * 
 * @Package com.vdaoyun.systemapi.mq
 *  
 * @ClassName: MQSendCallback
 *  
 * @Description: 发送回调
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月13日 上午9:59:35
 *
 */
public class MQSendCallback implements SendCallback {
	
	private static final Logger log = LoggerFactory.getLogger(MQSendCallback.class);

	@Override
	public void onException(OnExceptionContext arg0) {
		log.error(arg0.getException().getMessage());
	}

	@Override
	public void onSuccess(SendResult arg0) {
		log.debug(arg0.toString());
	}

}
