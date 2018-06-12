package com.vdaoyun.systemapi.web.mapper.warn;

import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceNotiRecord;

public interface DeviceNotiRecordMapper extends BaseMapper<DeviceNotiRecord> {

	public List<DeviceNotiRecord> selectPageInfo(Map<String, Object> param);
	
	public DeviceNotiRecord selectInfoByKey(Integer id);
}