/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.function.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.common.bean.TreeBean;
import com.vdaoyun.systemapi.web.base.function.dao.SysFunctionOperationMapper;
import com.vdaoyun.systemapi.web.base.function.model.SysFunctionOperation;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.function.service
 * 
 * @ClassName: SysFunctionOperationService
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:16:24
 */
@Service
public class SysFunctionOperationService extends BaseService<SysFunctionOperation> {

	@Autowired
	private SysFunctionOperationMapper sysFunctionOperationMapper;
	
	public List<TreeBean> selectTreeList(Integer roleId, String typeCode){
		return sysFunctionOperationMapper.selectTreeList(roleId, typeCode);
	}
	
	public List<SysFunctionOperation> selectList(Integer functionId){
		Example example = new Example(SysFunctionOperation.class);
		example.createCriteria().andEqualTo("functionId", functionId);
		return sysFunctionOperationMapper.selectByExample(example);
	}
	
	public int insert(SysFunctionOperation sysFunctionOperation){
		return sysFunctionOperationMapper.insertSelective(sysFunctionOperation);
	}
	
}