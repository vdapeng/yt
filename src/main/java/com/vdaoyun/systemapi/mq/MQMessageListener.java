package com.vdaoyun.systemapi.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.vdaoyun.systemapi.mq.model.MQDeviceRecordModel;
import com.vdaoyun.systemapi.mq.model.MQDeviceWarnModel;
import com.vdaoyun.systemapi.web.model.device.DeviceRecord;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;
import com.vdaoyun.systemapi.web.service.device.DeviceRecordService;
import com.vdaoyun.systemapi.web.service.warn.DeviceWarnRecordService;

/**
 * 
 * @Package com.vdaoyun.systemapi.listener
 *  
 * @ClassName: MQMessageListener
 *  
 * @Description: 监听mq消息
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月12日 下午8:00:11
 *
 */
public class MQMessageListener implements MessageListener {
	
	private static final Logger log = LoggerFactory.getLogger(MQMessageListener.class);
	
	@Autowired
	private DeviceRecordService deviceRecordService;
	@Autowired
	private DeviceWarnRecordService deviceWarnRecordService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String secondTopic = message.getUserProperties("mqttSecondTopic");
		log.info("=====================================");
		log.info("recv msg secondTopic:"+ secondTopic);
		log.info("=====================================");
		byte[] body = message.getBody();
		switch (secondTopic) {
		case MQTopic.DEVICE_TOPIC:
			if (body != null && body.length > 0) {
				MQDeviceRecordModel record = JSONObject.parseObject(body, MQDeviceRecordModel.class, Feature.AllowArbitraryCommas);
				DeviceRecord entity = new DeviceRecord(record);
				try {
					deviceRecordService.insert(entity);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
			break;
		case MQTopic.WARN_TOPIC:
			MQDeviceWarnModel record = JSONObject.parseObject(body, MQDeviceWarnModel.class, Feature.AllowArbitraryCommas);
			DeviceWarnRecord entity = new DeviceWarnRecord(record);
			deviceWarnRecordService.insert(entity);
			break;
		case MQTopic.CGQ_TOPIC:
			break;
		default:
			break;
		}
        return Action.CommitMessage;
	}

}
