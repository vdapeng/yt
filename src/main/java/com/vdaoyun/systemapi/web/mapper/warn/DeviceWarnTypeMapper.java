package com.vdaoyun.systemapi.web.mapper.warn;

import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnType;

public interface DeviceWarnTypeMapper extends BaseMapper<DeviceWarnType> {

	public List<DeviceWarnType> selectPageInfo(Map<String, Object> param);
	
	public DeviceWarnType selectInfoByKey(Integer id);
}