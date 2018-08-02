package com.vdaoyun.systemapi.scheduling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.vdaoyun.systemapi.common.utils.SmsUtils;
import com.vdaoyun.systemapi.web.model.user.User;
import com.vdaoyun.systemapi.web.model.warn.DeviceNotiRecord;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;
import com.vdaoyun.systemapi.web.service.user.UserService;
import com.vdaoyun.systemapi.web.service.warn.DeviceNotiRecordService;
import com.vdaoyun.systemapi.web.service.warn.DeviceWarnRecordService;

@Service
public class AlarmScheduling {
	
	@Autowired
	private DeviceNotiRecordService deviceNotiRecordService; 
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceWarnRecordService deviceWarnRecordService;
	
	private static final Logger log = LoggerFactory.getLogger(AlarmScheduling.class);

//	@Scheduled(cron = "0 0/1 * * * ? ")
	public void sensSms() {
		// 查询需要发送短信的报警记录
		List<DeviceNotiRecord> records = deviceNotiRecordService.selectUnRead();
		for (DeviceNotiRecord deviceNotiRecord : records) {
			// 
			User user = userService.selectByPrimaryKey(deviceNotiRecord.getUserId());
			// 查询报警记录
			DeviceWarnRecord deviceWarnRecord = deviceWarnRecordService.selectByPrimaryKey(deviceNotiRecord.getDeviceWarnRecordId());
			try {
				// 发送短信通知
				SendSmsResponse response = SmsUtils.sendAlarmNoti(user.getMobile(), JSON.toJSONString(deviceWarnRecord));
				if (response.getCode().equalsIgnoreCase("ok")) {
					// 短信发送成功之后，数据库记录
					deviceNotiRecordService.sendSms(deviceNotiRecord.getId(), response.getBizId());
				} else {
					log.error("\n================================\n\t"
							+ "TITLE: \t短信发送失败\n\t"
							+ "MESSAGE: \t{}\n\t"
							+ "CODE: \t{}\n"
							+ "================================", response.getMessage(), response.getCode());
				}
			} catch (ClientException e) {
				log.error("\n================================\n\t"
						+ "MESSAGE: {}\n"
						+ "================================", e.getLocalizedMessage());
			}
		}
	}

}
