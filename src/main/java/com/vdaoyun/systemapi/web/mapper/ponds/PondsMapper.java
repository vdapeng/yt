package com.vdaoyun.systemapi.web.mapper.ponds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;

public interface PondsMapper extends BaseMapper<Ponds> {

	public List<HashMap<String, Object>> selectPageInfo(Map<String, Object> param);
	
	public Ponds selectInfoByKey(Integer id);
	
	List<HashMap<String, Object>> selectPageInfoEx(HashMap<String, Object> param);
	
	HashMap<String, Object> selectInfoEx(Long id);
	
}