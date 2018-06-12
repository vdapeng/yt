package com.vdaoyun.systemapi.web.mapper.sensor;

import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;

public interface SensorMapper extends BaseMapper<Sensor> {

	public List<Sensor> selectPageInfo(Map<String, Object> param);
	
	public Sensor selectInfoByKey(Integer id);
}