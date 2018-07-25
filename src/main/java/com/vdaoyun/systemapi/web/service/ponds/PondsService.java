package com.vdaoyun.systemapi.web.service.ponds;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.systemapi.web.mapper.ponds.PondsMapper;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class PondsService extends BaseService<Ponds> {
	
	@Override
	public int delete(Object key) {
		Ponds entity = new Ponds();
		entity.setIsDel(YesOrNo.YES.toString());
		entity.setId((Long)key);
		return super.update(entity);
	} 
	
	@Override
	public int insert(Ponds entity) {
//		entity.setCreateDate(new Date());
		return super.insert(entity);
	}
	
	public List<Ponds> selectAll() {
		return mapper.selectAll();
	}
	
	@Autowired
	private PondsMapper rootMapper;
	
	public PageInfo<HashMap<String, Object>> selectPageInfo(
		Ponds entity, 
		Integer wdy_pageNum, 
		Integer wdy_pageSize, 
		String wdy_pageOrder, 
		String wdy_pageSort
	) throws Exception {
		Map<String, Object> param = new HashMap<>();
		entity.setIsDel(YesOrNo.NO.toString());
		param.put("entity", entity);
		if (StringUtils.isNotEmpty(wdy_pageOrder) && StringUtils.isNotEmpty(wdy_pageSort)) {
			param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		} else {
			param.put("orderByClause", "createDate DESC");
		}
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<HashMap<String, Object>> list = rootMapper.selectPageInfo(param);
		return new PageInfo<>(list);
	}
	
	
	@Transactional
	public Integer insertInfo(Ponds entity) {
		entity.setCreateDate(new Date());
		Integer result = super.insert(entity);
		if (result > 0) {
		}
		return result;
	}
	
	public PageInfo<HashMap<String, Object>> selectPageInfoEx(
			Ponds entity, 
			Integer wdy_pageNum, 
			Integer wdy_pageSize, 
			String wdy_pageOrder, 
			String wdy_pageSort) {
		HashMap<String, Object> param = new HashMap<>();
		entity.setIsDel(YesOrNo.NO.toString());
		param.put("entity", entity);
		param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<HashMap<String, Object>> list = rootMapper.selectPageInfoEx(param);
		return new PageInfo<>(list);
	}
	
	public HashMap<String, Object> selectInfoEx(Long id) {
		return rootMapper.selectInfoEx(id);
	}
	
	
	@SuppressWarnings("unchecked")
	public PageInfo<HashMap<String, Object>> selectListJsonData(Ponds entity, Integer wdy_pageNum, Integer wdy_pageSize,
			String wdy_pageOrder, String wdy_pageSort) {
		HashMap<String, Object> param = new HashMap<>();
		entity.setIsDel(YesOrNo.NO.toString());
		param.put("entity", entity);
		param.put("orderByClause", wdy_pageOrder + " " + wdy_pageSort);
		PageHelper.startPage(wdy_pageNum, wdy_pageSize);
		List<HashMap<String, Object>> list = rootMapper.selectListJsonData(param);
		for (HashMap<String, Object> hashMap : list) {
			Object sensorJsonData = hashMap.get("sensorJsonData");
			if (sensorJsonData != null) {
				((HashMap<String, Object>) sensorJsonData).put("data_json", JSON.parse(((HashMap<String, Object>) sensorJsonData).get("data_json").toString()));
			}
		}
		return new PageInfo<>(list);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectInfoJsonData(Long id) {
		HashMap<String, Object> hashMap = rootMapper.selectInfoJsonData(id);
		Object sensorJsonData = hashMap.get("sensorJsonData");
		if (sensorJsonData != null) {
			((HashMap<String, Object>) sensorJsonData).put("data_json", JSON.parse(((HashMap<String, Object>) sensorJsonData).get("data_json").toString()));
		}
		return hashMap;
	}
	
	public List<Ponds> search(String search) {
		Example example = new Example(Ponds.class);
		example.createCriteria().andLike("name", search + "%");
		example.or().andLike("address", "%" + search + "%");
		return mapper.selectByExample(example);
	}
	
	public int count() {
		Ponds ponds = new Ponds();
		ponds.setIsDel(YesOrNo.NO.toString());
		return mapper.selectCount(ponds);
	}
}
