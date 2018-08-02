package com.vdaoyun.systemapi.web.mapper.warn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;

public interface DeviceWarnRecordMapper extends BaseMapper<DeviceWarnRecord> {

	List<DeviceWarnRecord> selectPageInfo(Map<String, Object> param);
	
	List<HashMap<String, Object>> selectPageInfoEx(Map<String, Object> param);
	
	Long countPageInfoEx(Map<String, Object> param);
	
	HashMap<String, Object> selectInfoByKey(Long id);
	
	// 报警列表
	List<HashMap<String, Object>> alarmList(HashMap<String, Object> param);
	
	List<HashMap<String, Object>> alarmAllList(HashMap<String, Object> param);
	
	
	// 用于微信报警查询分页
	List<HashMap<String, Object>> pageInfoAlarmListForWx(HashMap<String, Object> param);
	
	// 微信报警查询
	List<HashMap<String, Object>> selectAlarmListForWx(HashMap<String, Object> param);
	
	// 管理后台报警查询
	List<HashMap<String, Object>> selectAlarmListForAdmin(HashMap<String, Object> param);
	
	//	用于管理后台报警查询分页
	List<HashMap<String, Object>> pageInfoAlarmListForAdmin(HashMap<String, Object> param);
	
	
	
}