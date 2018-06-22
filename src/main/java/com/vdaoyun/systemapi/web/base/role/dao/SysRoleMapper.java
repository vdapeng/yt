/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.role.dao;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.web.base.role.model.SysRole;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.dao
 * 
 * @ClassName: SysRoleMapper
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:17:44
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

	SysRole selectByUserId(Integer userId);
	
}