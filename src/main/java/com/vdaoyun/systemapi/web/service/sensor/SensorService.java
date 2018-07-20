package com.vdaoyun.systemapi.web.service.sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.mapper.sensor.SensorMapper;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;
import com.vdaoyun.systemapi.web.model.sensor.SensorConfig;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional
public class SensorService extends BaseService<Sensor> {
	
	public List<Sensor> select(Sensor sensor) {
		return mapper.select(sensor);
	}
	
	public void alarm(String[] codes, String terminalId) {
		Example example = new Example(Sensor.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("terminalId", terminalId);
		// 将该设备下所有探测器状态设置为非报警状态
		mapper.updateByExampleSelective(new Sensor(YesOrNo.NO.toString()), example);
		criteria.andIn("code", Arrays.asList(codes));
		// 将该设备下报警的探测器状态设置为报警状态
		mapper.updateByExampleSelective(new Sensor(YesOrNo.YES.toString()), example);
		
	}
	
//	@Override
//	public int delete(Object key) {
//		Sensor entity = new Sensor();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	@Override
	public int insert(Sensor entity) {
		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	public Sensor isExit(String terminalId, String code, Long pondsId) {
		Sensor record = new Sensor();
		record.setTerminalId(terminalId);
		record.setCode(code);
		record.setPondsId(pondsId);
		return mapper.selectOne(record);
	}
	
	@Autowired
	private SensorMapper rootMapper;
	
	public PageInfo<Sensor> selectPageInfo(
		Sensor entity, 
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
		List<Sensor> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	public Integer insertInfo(Sensor entity) {
		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	public List<HashMap<String, Object>> selectListByPondsId(Long pondsId) {
		return rootMapper.selectListByPondsId(pondsId);
	}
	
	public List<Sensor> selectSensorsByPondsId(Long pondsId) {
		Example example = new Example(Sensor.class);
		example.setDistinct(true);
		example.createCriteria().andEqualTo("pondsId", pondsId);
		return mapper.selectByExample(example);
	}
	
	public List<Sensor> selectSensorsByTerminalId(String terminalId, String code) {
		Example example = new Example(Sensor.class);
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("terminalId", terminalId);
		criteria.andEqualTo("code", code);
		return mapper.selectByExample(example);
	}

	public List<HashMap<String, Object>> selectListByCode(String code, String terminalId) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("groupCode", code);
		param.put("terminalId", terminalId);
		return rootMapper.selectListByCode(param);
	}
	
	public List<HashMap<String, Object>> selectListByGroupCode(String groupCode, String terminalId) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("groupCode", groupCode);
		param.put("terminalId", terminalId);
		return rootMapper.selectListByGroupCode(param);
	}
	
	public List<HashMap<String, Object>> selectListByPondsIdForJson(Long pondsId, String terminalId) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("pondsId", pondsId);
		param.put("terminalId", terminalId);
		return rootMapper.selectListByPondsIdForJson(param);
	}
	
	public List<HashMap<String, Object>> selectByTerminalId(
			String terminalId) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("terminalId", terminalId);
		param.put("orderByClause", "createDate DESC");
		return rootMapper.selectByTerminalId(param);
	}
	
	// 批量给塘口配置探测器
	public void batchConfig(String terminalId, Long pondsId, List<SensorConfig> sensorConfigs) {
		if (sensorConfigs.size() < 1) {
			return;
		}
		List<Sensor> sensors = new ArrayList<>();
		List<String> delSensorCodes = new ArrayList<>();
		for (SensorConfig sensorConfig : sensorConfigs) {
			if (sensorConfig.getIsEnable().equals(YesOrNo.YES.toString())) {
				sensors.add(new Sensor(terminalId, pondsId, sensorConfig.getCode(), sensorConfig.getName()));
			} else {
				delSensorCodes.add(sensorConfig.getCode());
			}
		}
		if (delSensorCodes.size() > 0) {
			Example example = new Example(Sensor.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("pondsId", pondsId);
			criteria.andEqualTo("terminalId", terminalId);
			criteria.andIn("code", delSensorCodes);
			mapper.deleteByExample(example);
		}
//		mapper.delete(new Sensor(terminalId, pondsId));
		if (sensors.size() < 1) {
			return;
		}
		for (Sensor sensor : sensors) {
			if (mapper.selectCount(sensor) < 1) {
				sensor.setCreateDate(new Date());
				mapper.insertSelective(sensor);
			}
		}
	}
	
	// 判断塘口是否绑定探测器
	public Boolean isBindSensor(Long pondsId) {
		Sensor sensor = new Sensor();
		sensor.setPondsId(pondsId);
		return mapper.selectCount(sensor) > 0;
	}
	
	// 批量配置 探测器是否报警
	public void batchConfigAlarm(List<Sensor> sensorList) {
		List<Long> alarmSensorIds = new ArrayList<>();
		List<Long> noAlarmSensorIds = new ArrayList<>();
		for (Sensor sensor : sensorList) {
			if (sensor.getIsAlarm().equalsIgnoreCase(YesOrNo.YES.toString())) {
				alarmSensorIds.add(sensor.getId());
			} else {
				noAlarmSensorIds.add(sensor.getId());
			}
		}
		Example example = new Example(Sensor.class);
		if (alarmSensorIds.size() > 0) {
			example.createCriteria().andIn("id", alarmSensorIds);
			mapper.updateByExampleSelective(new Sensor(YesOrNo.YES.toString()), example);
		}
		if (noAlarmSensorIds.size() > 0) {
			example.clear();
			example.createCriteria().andIn("id", noAlarmSensorIds);
			mapper.updateByExampleSelective(new Sensor(YesOrNo.NO.toString()), example);
		}
	}
	
	public void batchConfigNoti(List<Sensor> sensorList) {
		List<Long> notiSensorIds = new ArrayList<>();
		List<Long> noNoitSensorIds = new ArrayList<>();
		for (Sensor sensor : sensorList) {
			if (sensor.getIsNoti().equalsIgnoreCase(YesOrNo.YES.toString())) {
				notiSensorIds.add(sensor.getId());
			} else {
				noNoitSensorIds.add(sensor.getId());
			}
		}
		Example example = new Example(Sensor.class);
		Sensor sensor = new Sensor();
		if (notiSensorIds.size() > 0) {
			example.createCriteria().andIn("id", notiSensorIds);
			sensor.setIsNoti(YesOrNo.YES.toString());
			mapper.updateByExampleSelective(sensor, example);
		}
		if (noNoitSensorIds.size() >  0) {
			example.clear();
			example.createCriteria().andIn("id", noNoitSensorIds);
			sensor.setIsNoti(YesOrNo.NO.toString());
			mapper.updateByExampleSelective(sensor, example);
		}
	}
}
