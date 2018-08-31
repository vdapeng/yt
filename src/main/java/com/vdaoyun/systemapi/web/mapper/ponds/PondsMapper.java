package com.vdaoyun.systemapi.web.mapper.ponds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.ponds.Ponds;

@Mapper
public interface PondsMapper extends BaseMapper<Ponds> {

	public List<HashMap<String, Object>> selectPageInfo(Map<String, Object> param);
	
	public HashMap<String, Object> selectInfoByKey(Long id);
	
	List<HashMap<String, Object>> selectPageInfoEx(HashMap<String, Object> param);
	
	HashMap<String, Object> selectInfoEx(Long id);
	
	List<HashMap<String, Object>> selectListJsonData(HashMap<String, Object> param);
	
	HashMap<String, Object> selectInfoJsonData(Long id);
	
	// 查询塘口信息，包含我拥有的塘口和分享给我的塘口
	List<HashMap<String, Object>> selectMiniList(@Param("openid") String openid, @Param("orderByClause") String orderByClause);
	
}