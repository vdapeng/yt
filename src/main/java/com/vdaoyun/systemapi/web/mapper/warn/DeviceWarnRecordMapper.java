package com.vdaoyun.systemapi.web.mapper.warn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;

public interface DeviceWarnRecordMapper extends BaseMapper<DeviceWarnRecord> {

	public List<DeviceWarnRecord> selectPageInfo(Map<String, Object> param);
	
	List<HashMap<String, Object>> selectPageInfoEx(Map<String, Object> param);
	
	Long countPageInfoEx(Map<String, Object> param);
	
	public DeviceWarnRecord selectInfoByKey(Integer id);
}