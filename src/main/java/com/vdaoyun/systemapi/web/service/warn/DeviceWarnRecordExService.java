package com.vdaoyun.systemapi.web.service.warn;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;

@Service
public class DeviceWarnRecordExService extends BaseService<DeviceWarnRecord> {
	
	/**
	 * 
	 * @Title: version
	 *  
	 * @Description: 更新version字段，更新二级缓存用
	 *  
	 * @param id void
	 */
	public void version(Long id) {
		DeviceWarnRecord record;
		if (id == null) {
			PageHelper.startPage(1, 1);
			List<DeviceWarnRecord> records = mapper.select(new DeviceWarnRecord());
			if (!records.isEmpty()) {
				record = records.get(0);
			} else {
				return;
			}
		} else {
			record = mapper.selectByPrimaryKey(id);
		}
		if (record != null) {
			Long version = record.getVersion();
			DeviceWarnRecord deviceWarnRecord = new DeviceWarnRecord();
			deviceWarnRecord.setId(record.getId());
			deviceWarnRecord.setVersion(++version);
			mapper.updateByPrimaryKeySelective(deviceWarnRecord);
		}
	}
	
}
