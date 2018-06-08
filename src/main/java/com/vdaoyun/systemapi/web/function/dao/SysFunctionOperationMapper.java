/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.function.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vdaoyun.common.api.base.mapper.BaseMapper;
import com.vdaoyun.systemapi.common.bean.TreeBean;
import com.vdaoyun.systemapi.web.function.model.SysFunctionOperation;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.function.dao
 * 
 * @ClassName: SysFunctionOperationMapper
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:16:24
 */
public interface SysFunctionOperationMapper extends BaseMapper<SysFunctionOperation> {

	List<TreeBean> selectTreeList(@Param("roleId") Integer roleId, @Param("typeCode") String typeCode);
}