package com.vdaoyun.systemapi.web.service.warn;

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
import com.vdaoyun.systemapi.web.mapper.warn.DeviceWarnConfigMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnConfig;

@Service
public class DeviceWarnConfigService extends BaseService<DeviceWarnConfig> {
	
//	@Override
//	public int delete(Object key) {
//		DeviceWarnConfig entity = new DeviceWarnConfig();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	@Override
	public int insert(DeviceWarnConfig entity) {
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	@Autowired
	private DeviceWarnConfigMapper rootMapper;
	
	public PageInfo<DeviceWarnConfig> selectPageInfo(
		DeviceWarnConfig entity, 
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
		List<DeviceWarnConfig> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(DeviceWarnConfig entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}

}
