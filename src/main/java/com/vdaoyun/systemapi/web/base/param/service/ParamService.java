/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.param.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.systemapi.web.base.param.dao.ParamMapper;
import com.vdaoyun.systemapi.web.base.param.model.Param;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemapi.web.service.param
 * 
 * @ClassName: ParamService
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-7-17 10:34:23
 */
@Service
public class ParamService extends BaseService<Param> {
	
	@Autowired
	private ParamMapper paramMapper;
	
	/**
	 * 
	 * @Title: selectByName
	 *  
	 * @Description: 根据名称查询系统参数
	 *  
	 * @param username
	 * @param id
	 * @return Param
	 */
	public String selectByName(String name, Integer id){
		Example example = new Example(Param.class);
		example.createCriteria().andEqualTo("name", name)
		.andNotEqualTo("paramId", id)
		.andEqualTo("isDel", IConstant.YesOrNo.NO.toString());
		List<Param> list = paramMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0).getValue() : null;
	}
}