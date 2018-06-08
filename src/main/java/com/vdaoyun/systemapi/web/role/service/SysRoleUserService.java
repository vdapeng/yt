/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.role.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.systemapi.web.role.dao.SysRoleUserMapper;
import com.vdaoyun.systemapi.web.role.model.SysRoleUser;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.service
 * 
 * @ClassName: SysRoleUserService
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:17:44
 */
@Service
public class SysRoleUserService extends BaseService<SysRoleUser> {

	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	
	public int insert(SysRoleUser sysRoleUser){
		return sysRoleUserMapper.insertSelective(sysRoleUser);
	}
	
	public int insert(int roleId, int userId){
		SysRoleUser sysRoleUser = new SysRoleUser();
		sysRoleUser.setRoleId(roleId);
		sysRoleUser.setUserId(userId);
		return insert(sysRoleUser);
	}

}