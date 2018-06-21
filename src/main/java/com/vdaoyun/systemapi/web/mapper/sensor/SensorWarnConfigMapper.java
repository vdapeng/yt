package com.vdaoyun.systemapi.web.mapper.sensor;

import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.sensor.SensorWarnConfig;

public interface SensorWarnConfigMapper extends BaseMapper<SensorWarnConfig> {

	public List<SensorWarnConfig> selectPageInfo(Map<String, Object> param);
	
	public SensorWarnConfig selectInfoByKey(Integer id);
}