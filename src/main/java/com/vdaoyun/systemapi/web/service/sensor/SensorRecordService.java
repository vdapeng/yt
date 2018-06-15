package com.vdaoyun.systemapi.web.service.sensor;

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
import com.vdaoyun.systemapi.web.mapper.sensor.SensorRecordMapper;
import com.vdaoyun.systemapi.web.model.sensor.SensorRecord;

@Service
public class SensorRecordService extends BaseService<SensorRecord> {
	
//	@Override
//	public int delete(Object key) {
//		SensorRecord entity = new SensorRecord();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	@Override
	public int insert(SensorRecord entity) {
//		entity.setCreateDate(new Date());
		
		return super.insert(entity);
	}
	
	@Override
	public int batchInsert(List<SensorRecord> entityList) {
		return mapper.insertList(entityList);
	}
	
	@Autowired
	private SensorRecordMapper rootMapper;
	
	public PageInfo<SensorRecord> selectPageInfo(
		SensorRecord entity, 
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
		List<SensorRecord> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(SensorRecord entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}

}
