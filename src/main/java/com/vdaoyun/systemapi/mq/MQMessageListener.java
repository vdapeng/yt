package com.vdaoyun.systemapi.mq;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import com.vdaoyun.systemapi.web.model.sensor.SensorRecord;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;
import com.vdaoyun.systemapi.web.service.device.DeviceRecordService;
import com.vdaoyun.systemapi.web.service.sensor.SensorRecordService;
import com.vdaoyun.systemapi.web.service.warn.DeviceWarnRecordService;
import com.vdaoyun.util.DateUtil;

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

	@Override
	public Action consume(Message message, ConsumeContext context) {
		String secondTopic = message.getUserProperties("mqttSecondTopic");
		log.info("=====================================");
		log.info("recv msg secondTopic:"+ secondTopic);
		log.info("=====================================");
		byte[] body = message.getBody();
		switch (secondTopic) {
		case MQConstants.DEVICE_TOPIC:
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
		case MQConstants.WARN_TOPIC:
			MQDeviceWarnModel record = JSONObject.parseObject(body, MQDeviceWarnModel.class, Feature.AllowArbitraryCommas);
			DeviceWarnRecord entity = new DeviceWarnRecord(record);
			deviceWarnRecordService.insert(entity);
			break;
		case MQConstants.CGQ_TOPIC:
			MQSensorRecordModel data = JSON.parseObject(body, MQSensorRecordModel.class, Feature.AllowArbitraryCommas);
			String terminalId = data.getTerminalID();				// 设备编号
			Date postTime = new Date();		 				// 上传时间
			Integer SampeFrequency = data.getSampeFrequency();		// 数据采集频率
			Set<String> keys = data.getData().get(0).keySet();		// 数据所有key
			List<HashMap<String, Object>> list = data.getData();	// 采集到的数据列表
			HashMap<String, Object> item = null;					// 临时变量，用于存储data
			List<SensorRecord> sensorRecords = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				item = list.get(i);
				for (String key : keys) {
					if (!key.contains("_T")) {
						SensorRecord sensorRecord = new SensorRecord();
						sensorRecord.setCode(key);
						sensorRecord.setTerminalId(terminalId);
						sensorRecord.setValue(item.get(key).toString());
						sensorRecord.setPostTime(postTime);
						if (item.containsKey(key + "_T") && !item.containsKey(key + "_Temperature")) {
							sensorRecord.setTemperatureValue(item.get(key + "_T").toString());				// 获取传感器温度值
						} else {
							sensorRecord.setTemperatureValue(item.get(key + "_Temperature").toString());	// 获取传感器温度值
						}
						sensorRecord.setDataTime(DateUtil.subtractDate(postTime, SampeFrequency/60 * (list.size() - 1 - i)));	// 计算数据生产时间
						sensorRecords.add(sensorRecord);
					}
				}
			}
			sensorRecordService.batchInsert(sensorRecords);
			break;
		default:
			break;
		}
        return Action.CommitMessage;
	}
	
}
