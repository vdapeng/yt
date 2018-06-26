package com.vdaoyun.systemapi.web.service.sensor;

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
import com.vdaoyun.systemapi.web.mapper.sensor.SensorMapper;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;

@Service
@Transactional
public class SensorService extends BaseService<Sensor> {
	
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
	
	public Sensor isExit(String terminalId, String code) {
		Sensor record = new Sensor();
		record.setTerminalId(terminalId);
		record.setCode(code);
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
	
}
