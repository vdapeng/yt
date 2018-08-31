package com.vdaoyun.systemapi.mq;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vdaoyun.systemapi.mq.model.MQDeviceRecordData;
import com.vdaoyun.systemapi.mq.model.MQDeviceRecordModel;
import com.vdaoyun.systemapi.mq.model.MQDeviceWarnModel;
import com.vdaoyun.systemapi.mq.model.MQSensorRecordModel;

/**
 * 
 * @Package com.vdaoyun.systemapi.mq
 *  
 * @ClassName: MQTest
 *  
 * @Description: MQ测试类，通过定时器，定时发送MQ消息
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月3日 下午4:33:43
 *
 */
//@Service
public class MQTest {

	private static final Logger log = LoggerFactory.getLogger(MQTest.class);

	@Autowired
	private MQProducerService mqProducerService;
	

	/**
	 * 
	 * @Title: sendWarnData
	 *  
	 * @Description: 模拟设备运行数据， 每15分钟推送一次
	 *   void
	 */
//	@Scheduled(cron = "0 0/15 * * * ? ")
	public void sendDeviceRecordData() {
		// 模拟业务数据
		MQDeviceRecordData item = new MQDeviceRecordData();
		item.setSolarVoltage(18.2 + RandomUtils.nextDouble(0, 5));
		item.setBatteryVoltage(35.6 + RandomUtils.nextDouble(0, 5));
		item.setBatteryTemperature(45.3 + RandomUtils.nextDouble(0, 5));
		item.setSystemTemperature(35.6 + RandomUtils.nextDouble(0, 5));
		item.setBatteryLevel(97 + RandomUtils.nextDouble(0, 5));
		item.setShellTemperature(36.3 + RandomUtils.nextDouble(0, 5));
		item.setGPS("9.5080440.0,3025.9864N,11424.8529E,2.1,56.0,2,92.23,0.0,0.0,140518,08");
		item.setPowerKey("7.220180-25 02:25:56");
		item.setChargerStatus(1);
		List<MQDeviceRecordData> data = new ArrayList<>();
		data.add(item);
		MQDeviceRecordModel record = new MQDeviceRecordModel();
		record.setTerminalID(Arrays.asList(new String[] {"TK232", "TK233"}).get(RandomUtils.nextInt(0, 1)));
//		record.setPostTime(new Date());
		record.setSampeFrequency(0);
		record.setData(data);
		SendMessage(MQConstants.DEVICE_TOPIC, record);
	}
	
	// 发送mq消息
	private void SendMessage(String topic, Object record) {
		byte[] body = null;
		String content = JSONObject.toJSONString(record, true);
		try {
			body = content.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		mqProducerService.send(topic, body);
	}
	
	/**
	 * 
	 * @Title: sendDeviceWarnData
	 *  
	 * @Description: 模拟设备报警，每三十分钟一次
	 *   void
	 */
	@Scheduled(cron = "0 0/3 * * * ? ")
	public void sendDeviceWarnData() {
		MQDeviceWarnModel record = new MQDeviceWarnModel();
		record.setTerminalID(Arrays.asList(new String[] {"TK232", "TK233"}).get(RandomUtils.nextInt(0, 1)));
//		record.setPostTime(new Date());
		record.setAlaram_Business("PH_1&DO_1");
		record.setAlaram_Equipment("BATTERYLOW&TEMPERATUREHIGH");
		SendMessage(MQConstants.WARN_TOPIC, record);
	}
//	
//	@Scheduled(cron = "0 0/1 * * * ? ")
//	public void sendToClient() {
//		byte[] body = "HELLO WORLD".getBytes();
//		mqProducerService.sendToClient("ADMIN", body);
//	}
	
//	public static void main(String[] args) {
//		Optional<String> test = Optional.ofNullable(null).map((str) -> str.toString().toUpperCase());
//		System.out.println(test.orElse("TEST IS NULL"));
//		Optional.ofNullable(test.orElse("THIS IS DEFAULT VALUE")).map(val -> val.toString().toUpperCase()).ifPresent(val -> {
//			System.out.println(val);
//		});
//		System.out.println(Optional.ofNullable(test.orElse("THIS IS DEFAULT VALUE")).filter(u -> u.length() > 10).orElseThrow(() -> new NullPointerException()));
//	}
	
	/**
	 * 
	 * @Title: sendDeviceWarnData
	 *  
	 * @Description: 模拟传感器运行数据，每十五分钟一次
	 *   void
	 */
//	@Scheduled(cron = "0 0/15 * * * ? ")
	public void sendCGQRecordData() {
		// 模拟业务数据
		HashMap<String, Object> item = new HashMap<>();
		item.put("PH_1", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("PH_1_Temperature", 40 + RandomUtils.nextDouble(0, 5));
		item.put("PH_2", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("PH_2_Temperature", 40 + RandomUtils.nextDouble(0, 5));
		item.put("PH_3", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("PH_3_T", 40 + RandomUtils.nextDouble(0, 5));
		item.put("PH_4", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("PH_4_Temperature", 40 + RandomUtils.nextDouble(0, 5));
		item.put("DO_1", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("DO_1_Temperature", 40 + RandomUtils.nextDouble(0, 5));
		item.put("DO_2", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("DO_2_Temperature", 40 + RandomUtils.nextDouble(0, 5));
		item.put("DO_3", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("DO_3_Temperature", 40 + RandomUtils.nextDouble(0, 5));
		item.put("DO_4", 8.2 + RandomUtils.nextDouble(0, 5));
		item.put("DO_4_Temperature", 40 + RandomUtils.nextDouble(0, 5));
		List<HashMap<String, Object>> data = new ArrayList<>();
		data.add(item);
		data.add(item);
		data.add(item);
		MQSensorRecordModel record = new MQSensorRecordModel();
		record.setTerminalID(Arrays.asList(new String[] {"TK232", "TK233"}).get(RandomUtils.nextInt(0, 1)));
//		record.setPostTime(new Date());
		record.setSampeFrequency(300);
		record.setData(data);
		SendMessage(MQConstants.CGQ_TOPIC, JSON.toJSON(record));
	}

}
