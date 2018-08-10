package com.vdaoyun.systemapi.mq;

import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.vdaoyun.systemapi.mq.model.MQDeviceRecordModel;
import com.vdaoyun.systemapi.mq.model.MQDeviceWarnModel;
import com.vdaoyun.systemapi.mq.model.MQSensorRecordModel;
import com.vdaoyun.systemapi.web.model.device.DeviceRecord;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;
import com.vdaoyun.systemapi.web.service.device.DeviceRecordService;
import com.vdaoyun.systemapi.web.service.sensor.SensorRecordJsonService;
import com.vdaoyun.systemapi.web.service.sensor.SensorRecordService;
import com.vdaoyun.systemapi.web.service.sensor.SensorService;
import com.vdaoyun.systemapi.web.service.warn.DeviceNotiRecordService;
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
	@Autowired
	private SensorRecordService sensorRecordService;
	@Autowired
	private SensorRecordJsonService sensorRecordJsonService;
	@Autowired
	private DeviceNotiRecordService deviceNotiRecordService;
	@Autowired
	private SensorService sensorService;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String secondTopic = message.getUserProperties("mqttSecondTopic");
		byte[] body = message.getBody(); // 获取mq数据
		String bodyJson = JSON.toJSONString(JSONObject.parse(body, Feature.AllowArbitraryCommas));
		log.debug("\n=====================================\n\t"
				+ "SECONDTOPIC: \t{}\n\t"
				+ "CONTENT: \t{}\n\t"
				+ "DATETIME:\t{}\n"
				+ "=====================================", 
				secondTopic, bodyJson, LocalDateTime.now());
		if (secondTopic.contains("BusinessData")) { 																// 接收到业务数据
			MQSensorRecordModel data = JSON.parseObject(bodyJson, MQSensorRecordModel.class);
			sensorRecordService.insertRecord(data);
			sensorRecordJsonService.insertRecord(data);
			
			String alarmBusiness = data.getAlaram_Business() == null ? "" : data.getAlaram_Business();
			sensorService.alarm(alarmBusiness.trim().split(MQConstants.WARN_SEPARATOR), data.getTerminalID());		// 更新传感器报警状态
			
			if (StringUtils.isNotEmpty(alarmBusiness)) {					// 如果存在报警信息
				DeviceWarnRecord entity = new DeviceWarnRecord(data);
				deviceWarnRecordService.insert(entity);						// 1. 新增报警记录
				deviceNotiRecordService.sendWxMpTemplateMessage(entity);	// 2. 发送微信通知
			}
			
		} else if (secondTopic.contains("Alarm")) { 											// 接收到报警数据
			MQDeviceWarnModel record = JSON.parseObject(bodyJson, MQDeviceWarnModel.class);
			DeviceWarnRecord entity = new DeviceWarnRecord(record);
			deviceWarnRecordService.alarm(entity);												// 1. 修改传感器报警状态，并添加报警记录
			deviceNotiRecordService.sendWxMpTemplateMessage(entity);							// 2. 报警发送微信通知
			
		} else if (secondTopic.contains("EquipmentData")) {										// 接收到设备数据
			MQDeviceRecordModel record = JSON.parseObject(bodyJson, MQDeviceRecordModel.class);
			DeviceRecord entity = new DeviceRecord(record);			
			deviceRecordService.insert(entity);													// 1. 添加设备运行记录
		}
//		switch (secondTopic) {
//		case MQConstants.DEVICE_TOPIC:
//			if (body != null && body.length > 0) {
//				MQDeviceRecordModel record = JSON.parseObject(bodyJson, MQDeviceRecordModel.class);
//				DeviceRecord entity = new DeviceRecord(record);
//				deviceRecordService.insert(entity);
//			}
//			break;
//		case MQConstants.WARN_TOPIC:
//			MQDeviceWarnModel record = JSON.parseObject(bodyJson, MQDeviceWarnModel.class);
//			DeviceWarnRecord entity = new DeviceWarnRecord(record);
//			deviceWarnRecordService.alarm(entity);
//			// 报警发送微信通知
//			deviceNotiRecordService.sendWxMpTemplateMessage();
//			break;
//		case MQConstants.CGQ_TOPIC:
//			MQSensorRecordModel data = JSON.parseObject(bodyJson, MQSensorRecordModel.class);
//			sensorRecordService.insertRecord(data);
//			sensorRecordJsonService.insertRecord(data);
//			break;
//		default:
//			break;
//		}
        return Action.CommitMessage;
	}
	
}
