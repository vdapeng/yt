package com.vdaoyun.systemapi.web.mapper.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.device.Device;

public interface DeviceMapper extends BaseMapper<Device> {

	public List<Device> selectPageInfo(Map<String, Object> param);
	
	public Device selectInfoByKey(String id);
	
	// 通过塘口编号和终端编号查询，该塘口下终端信息和探测器信息
	HashMap<String, Object> selectInfoByPondsId(HashMap<String, Object> param);
}