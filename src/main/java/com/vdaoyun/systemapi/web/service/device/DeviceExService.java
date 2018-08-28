package com.vdaoyun.systemapi.web.service.device;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.model.device.Device;

@Service
@Transactional
public class DeviceExService extends BaseService<Device> {
	
	public void transfer(String terminalId, Long toUserId) {
		Device record = new Device();
		record.setUserId(toUserId);
		record.setTerminalId(terminalId);
		mapper.updateByPrimaryKeySelective(record);
	}

}
