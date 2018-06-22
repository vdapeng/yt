/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.function.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.systemapi.common.bean.TreeBean;
import com.vdaoyun.systemapi.common.utils.CommonUtil;
import com.vdaoyun.systemapi.web.base.function.dao.SysFunctionMapper;
import com.vdaoyun.systemapi.web.base.function.model.SysFunction;
import com.vdaoyun.util.StringTool;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.function.service
 * 
 * @ClassName: SysFunctionService
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 14:49:58
 */
@Service
public class SysFunctionService extends BaseService<SysFunction> {
	@Autowired
	private SysFunctionMapper sysFunctionMapper;
	@Autowired
	private SysFunctionOperationService sysFunctionOperationService;

	/**
	 * 
	 * @Title: selectList
	 *  
	 * @Description: 根据角色编号获取菜单列表
	 *  
	 * @param roleid
	 * @return List<sysFunction>
	 */
	public List<SysFunction> selectList(Integer roleId){
		List<SysFunction> list = sysFunctionMapper.selectListByRoleId(roleId);
		return getFunctionList(list);
	}
	
	/**
	 * 
	 * @Title: selectTreeList
	 *  
	 * @Description: 查询角色菜单权限列表
	 *  
	 * @param roleId
	 * @return List<TreeBean>
	 */
	public List<TreeBean> selectPrivilege(Integer roleId, String typeCode){
		List<TreeBean> functionlist = sysFunctionMapper.selectTreeList(roleId, typeCode);
		List<TreeBean> operationList = sysFunctionOperationService.selectTreeList(roleId, typeCode);
		for (TreeBean treeBean : operationList) {
			for (TreeBean function : functionlist) {
				if(function.getId() == treeBean.getPid()){
					if(function.getSubList() == null){
						function.setSubList(new ArrayList<TreeBean>());
					}
					function.getSubList().add(treeBean);
					break;
				}
			}
		}
		return CommonUtil.getTreeList(functionlist);
	}
	
	/**
	 * 
	 * @Title: selectList
	 *  
	 * @Description: 根据分类编码获取菜单列表
	 *  
	 * @param typeCode
	 * @return List<SysFunction>
	 */
	public List<SysFunction> selectList(String typeCode){
		Example example = new Example(SysFunction.class);
		example.createCriteria()
		.andEqualTo("typeCode", typeCode)
		.andEqualTo("isDel", IConstant.YesOrNo.NO.toString());
		return getFunctionList(sysFunctionMapper.selectByExample(example));
	}
	
	public int insert(SysFunction sysFunction){
		sysFunction.setIsDel(IConstant.YesOrNo.NO.toString());
		sysFunction.setCreateOn(StringTool.newDate());
		return sysFunctionMapper.insertSelective(sysFunction);
	}
	
	
	private List<SysFunction> getFunctionList(List<SysFunction> list){
		List<SysFunction> functionList = new ArrayList<SysFunction>();
		for (SysFunction sysFunction : list) {
			int functionLevel = sysFunction.getFunctionLevel();
			if(functionLevel == 0){
				functionList.add(sysFunction);
			} else if(functionLevel == 1){
				for (SysFunction sysFunction2 : functionList) {
					if(sysFunction.getParentFunctionId().toString().equals(sysFunction2.getFunctionId().toString())){
						if(sysFunction2.getSubList() == null || sysFunction2.getSubList().size()==0){
							sysFunction2.setSubList(new ArrayList<SysFunction>());
						}
						sysFunction2.getSubList().add(sysFunction);
						break;
					}
				}
			} else if(functionLevel == 2){
				for (SysFunction sysFunction2 : functionList) {
					if(sysFunction2.getSubList() != null){
						for (SysFunction sysFunction3 : sysFunction2.getSubList()) {
							if(sysFunction.getParentFunctionId().toString().equals(sysFunction3.getFunctionId().toString())){
								if(sysFunction3.getSubList() == null){
									sysFunction3.setSubList(new ArrayList<SysFunction>());
								}
								sysFunction3.getSubList().add(sysFunction);
								break;
							}
						}
					}
				}
			}
		}
		return functionList;
	}
}