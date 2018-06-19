package com.vdaoyun.systemapi.web.mapper.ponds;

import java.util.List;
import java.util.Map;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.model.ponds.PondsShareRecord;

public interface PondsShareRecordMapper extends BaseMapper<PondsShareRecord> {

	public List<PondsShareRecord> selectPageInfo(Map<String, Object> param);
	
	public PondsShareRecord selectInfoByKey(Integer id);
}