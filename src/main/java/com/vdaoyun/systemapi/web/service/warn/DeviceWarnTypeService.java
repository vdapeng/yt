package com.vdaoyun.systemapi.web.service.warn;

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
import com.vdaoyun.systemapi.web.mapper.warn.DeviceWarnTypeMapper;
import com.vdaoyun.systemapi.web.model.warn.DeviceWarnType;

@Service
public class DeviceWarnTypeService extends BaseService<DeviceWarnType> {
	
//	@Override
//	public int delete(Object key) {
//		DeviceWarnType entity = new DeviceWarnType();
//		entity.setIsDel(YesOrNo.YES.toString());
//		entity.setId((Integer)key);
//		return super.update(entity);
//	} 
	
	/**
	 * 
	 * @Title: selectAllByTerminalId
	 *  
	 * @Description: 通过设备编号查询所有报警类型信息，包含是否报警配置
	 *  
	 * @param terminalId	设备编号
	 * @return List<DeviceWarnType>
	 */
	public List<DeviceWarnType> selectAllByTerminalId(String terminalId) {
		return rootMapper.selectAllByTerminalId(terminalId);
	}
	
	@Override
	public int insert(DeviceWarnType entity) {
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	@Autowired
	private DeviceWarnTypeMapper rootMapper;
	
	public PageInfo<DeviceWarnType> selectPageInfo(
		DeviceWarnType entity, 
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
			param.put("orderByClause", "name DESC");
		}
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<DeviceWarnType> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(DeviceWarnType entity) {
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}

}
