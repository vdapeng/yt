package com.vdaoyun.systemapi.web.service.warn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.mq.MQConstants;
import com.vdaoyun.systemapi.web.mapper.warn.DeviceWarnRecordMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnRecord;
import com.vdaoyun.systemapi.web.service.sensor.SensorService;

@Service
@Transactional
public class DeviceWarnRecordService extends BaseService<DeviceWarnRecord> {
	
//	@Override
//	public int delete(Object key) {
//		DeviceWarnRecord entity = new DeviceWarnRecord();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	public void removeByTer(String terminalId) {
		DeviceWarnRecord record = new DeviceWarnRecord();
		record.setTerminalId(terminalId);
		mapper.delete(record);
	}
	
	@Override
	public int insert(DeviceWarnRecord entity) {
		entity.setVersion((long) 0);
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	public HashMap<String, Object> selectInfoByKey(Long id) {
		return rootMapper.selectInfoByKey(id);
	}
	
	@Autowired
	private SensorService sensorService;
	
	/**
	 * 
	* @Title: 设备报警 
	* @Description: 当设备报警时调用；1：新增报警记录 2：将报警探测器状态修改为报警状态
	* @param @param entity    设定文件 
	* @return void    返回类型 
	* @throws
	 */
//	@Async
	public void alarm(DeviceWarnRecord entity) {
		entity.setVersion((long) 0);
		mapper.insert(entity);
		String alaramBusiness = entity.getAlaramBusiness();
		if (StringUtils.isNotEmpty(alaramBusiness)) {
			String[] alarms = alaramBusiness.split(MQConstants.WARN_SEPARATOR);
			sensorService.alarm(alarms, entity.getTerminalId());
		}
	}
	
	@Autowired
	private DeviceWarnRecordMapper rootMapper;
	
	public PageInfo<DeviceWarnRecord> selectPageInfo(
		DeviceWarnRecord entity, 
		Integer wdy_pageNum, 
		Integer wdy_pageSize, 
		String wdy_pageOrder, 
		String wdy_pageSort
	) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("entity", entity);
		if (StringUtils.isNotEmpty(wdy_pageOrder) && StringUtils.isNotEmpty(wdy_pageSort)) {
			param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		} else {
			param.put("orderByClause", "createDate DESC");
		}
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<DeviceWarnRecord> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	public PageInfo<HashMap<String, Object>> selectPageInfoEx(
			HashMap<String, Object> entity, 
			Integer wdy_pageNum, 
			Integer wdy_pageSize, 
			String wdy_pageOrder, 
			String wdy_pageSort
		) throws Exception {
			wdy_pageNum = wdy_pageNum < 1 ? 1 : wdy_pageNum;
			int begin = (wdy_pageNum - 1) * wdy_pageSize;
			int end = begin + 10;
			Map<String, Object> param = new HashMap<>();
			param.put("entity", entity);
			param.put("begin", begin);
			param.put("end", end);
			param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
//			PageHelper.startPage(wdy_pageNum, wdy_pageSize);
			List<HashMap<String, Object>> list = rootMapper.selectPageInfoEx(param);
			PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>(list);
			Long total = rootMapper.countPageInfoEx(entity);
			pageInfo.setTotal(total);
			pageInfo.setIsFirstPage(wdy_pageNum <= 1);
			pageInfo.setIsLastPage(end >= total);
			return pageInfo;
		}
	
	
	@Transactional
	public Integer insertInfo(DeviceWarnRecord entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}

	public PageInfo<HashMap<String, Object>> alarmList(
			HashMap<String, Object> entity, 
			Integer wdy_pageNum, 
			Integer wdy_pageSize, 
			String wdy_pageOrder, 
			String wdy_pageSort) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("begin", (wdy_pageNum - 1) * wdy_pageSize);
		param.put("end", wdy_pageSize);
		param.put("entity", entity);
		param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		if (entity.containsKey("openid")) {
			Page<HashMap<String, Object>> page = PageHelper.startPage(wdy_pageNum, wdy_pageSize);
			rootMapper.pageInfoAlarmListForWx(param);
			PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>(page, 0);
			pageInfo.setList(rootMapper.selectAlarmListForWx(param));
			return pageInfo;
//			return new PageInfo<>(rootMapper.alarmList(param));
		}
		Page<HashMap<String, Object>> page = PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		rootMapper.pageInfoAlarmListForAdmin(param);
		PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>(page, 0);
		pageInfo.setList(rootMapper.selectAlarmListForAdmin(param));
		return pageInfo;
//		return new PageInfo<>(rootMapper.alarmAllList(param));
	}
	
	public int count() {
		DeviceWarnRecord record = new DeviceWarnRecord();
		return mapper.selectCount(record);
	}
	
	public void version(Long id, Long version) {
		DeviceWarnRecord record = new DeviceWarnRecord();
		record.setId(id);
		record.setVersion(++version);
		mapper.updateByPrimaryKeySelective(record);
	}
	
}
