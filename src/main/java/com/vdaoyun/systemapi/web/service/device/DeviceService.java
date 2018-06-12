package com.vdaoyun.systemapi.web.service.device;

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
import com.vdaoyun.systemapi.web.mapper.device.DeviceMapper;
import com.vdaoyun.systemapi.web.model.device.Device;

@Service
public class DeviceService extends BaseService<Device> {
	
	@Override
	public int delete(Object key) {
		Device entity = new Device();
		entity.setTerminalId((String) key);
		return mapper.delete(entity);
	} 
	
	@Override
	public int insert(Device entity) {
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	@Autowired
	private DeviceMapper rootMapper;
	
	public PageInfo<Device> selectPageInfo(
		Device entity, 
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
		List<Device> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(Device entity) {
		entity.setCreateDate(new Date());
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}

}
