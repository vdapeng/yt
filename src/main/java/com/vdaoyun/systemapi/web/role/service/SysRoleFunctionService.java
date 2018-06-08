/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.role.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.role.dao.SysRoleFunctionMapper;
import com.vdaoyun.systemapi.web.role.model.SysRoleFunction;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.service
 * 
 * @ClassName: SysRoleFunctionService
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:17:44
 */
@Service
public class SysRoleFunctionService extends BaseService<SysRoleFunction> {

	@Autowired
	private SysRoleFunctionMapper sysRoleFunctionMapper;
	
	/**
	 * 
	 * @Title: deleteByRoleId
	 *  
	 * @Description: 根据角色编号删除记录
	 *  
	 * @param roleId void
	 */
	public void deleteByRoleId(int roleId){
		Example example = new Example(SysRoleFunction.class);
		example.createCriteria().andEqualTo("roleId", roleId);
		sysRoleFunctionMapper.deleteByExample(example);
	}
	
	public int insert(SysRoleFunction sysRoleFunction){
		return sysRoleFunctionMapper.insertSelective(sysRoleFunction);
	}
}