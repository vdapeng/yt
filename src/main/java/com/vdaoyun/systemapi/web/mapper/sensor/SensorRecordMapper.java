package com.vdaoyun.systemapi.web.mapper.sensor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.sensor.SensorRecord;

public interface SensorRecordMapper extends BaseMapper<SensorRecord> {

	public List<SensorRecord> selectPageInfo(Map<String, Object> param);
	
	public SensorRecord selectInfoByKey(Integer id);
	
	
	List<HashMap<String, Object>> selectEchartData(Map<String, Object> param);
}