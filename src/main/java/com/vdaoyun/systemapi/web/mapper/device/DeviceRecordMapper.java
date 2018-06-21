package com.vdaoyun.systemapi.web.mapper.device;

import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.device.DeviceRecord;

public interface DeviceRecordMapper extends BaseMapper<DeviceRecord> {

	public List<DeviceRecord> selectPageInfo(Map<String, Object> param);
	
	public DeviceRecord selectInfoByKey(Integer id);
	
	List<DeviceRecord> selectEchartData(Map<String, Object> param);
}