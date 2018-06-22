/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.function.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.common.bean.TreeBean;
import com.vdaoyun.systemapi.web.base.function.model.SysFunction;


/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.function.dao
 * 
 * @ClassName: SysFunctionMapper
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 14:49:58
 */
public interface SysFunctionMapper extends BaseMapper<SysFunction> {

	List<SysFunction> selectListByRoleId(Integer roleId);
	
	List<TreeBean> selectTreeList(@Param("roleId") Integer roleId,@Param("typeCode") String typeCode);
}