package com.vdaoyun.systemapi.web.mapper.warn;

import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnConfig;

public interface DeviceWarnConfigMapper extends BaseMapper<DeviceWarnConfig> {

	public List<DeviceWarnConfig> selectPageInfo(Map<String, Object> param);
	
	public DeviceWarnConfig selectInfoByKey(Integer id);
}