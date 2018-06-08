/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.role.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.role.dao.SysRoleMapper;
import com.vdaoyun.systemapi.web.role.model.SysRole;
import com.vdaoyun.systemapi.web.role.model.SysRoleFunction;
import com.vdaoyun.util.StringTool;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.service
 * 
 * @ClassName: SysRoleService
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:17:44
 */
@Service
public class SysRoleService extends BaseService<SysRole> {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleFunctionService sysRoleFunctionService;

	/**
	 * 
	 * @Title: add
	 * 
	 * @Description: 添加角色信息
	 * 
	 * @param sysRole
	 * @return AjaxJson
	 */
	public AjaxJson add(SysRole sysRole) {
		AjaxJson j = new AjaxJson();
		if (roleCodeIsExist(sysRole.getRoleCode())) {
			j.setSuccess(false);
			j.setMsg("角色编码已经存在");
		} else {
			insert(sysRole);
		}
		return j;
	}

	/**
	 * 
	 * @Title: savePrivilege
	 * 
	 * @Description: 保存角色权限
	 * 
	 * @param roleId
	 * @param menu
	 * @param operation
	 * @return AjaxJson
	 */
	public void savePrivilege(Integer roleId, String menu, String operation) {
		// 删除菜单权限
		sysRoleFunctionService.deleteByRoleId(roleId);
		// 保存菜单权限
		List<SysRoleFunction> list = new ArrayList<SysRoleFunction>();
		String[] menus = menu.split(",");
		for (int i = 1; i < menus.length; i++) {
			SysRoleFunction function = new SysRoleFunction();
			function.setRoleId(roleId);
			function.setFunctionId(NumberUtils.toInt(menus[i]));
			list.add(function);
		}
		if (list.size() > 0) {
			sysRoleFunctionService.batchInsert(list);
		}
		// 保存操作权限
		SysRole sysRole = new SysRole();
		sysRole.setRoleId(roleId);
		sysRole.setOperation(operation);
		update(sysRole);
	}

	/**
	 * 
	 * @Title: selectByUserId
	 * 
	 * @Description: 根据用户编号查询角色信息
	 * 
	 * @param userId
	 * @return SysRole
	 */
	public SysRole selectByUserId(int userId) {
		return sysRoleMapper.selectByUserId(userId);
	}

	/**
	 * 
	 * @Title: roleCodeIsExist
	 * 
	 * @Description: 角色编码是否存在
	 * 
	 * @param roleCode
	 * @return boolean
	 */
	public boolean roleCodeIsExist(String roleCode) {
		Example example = new Example(SysRole.class);
		example.createCriteria().andEqualTo("roleCode", roleCode);
		List<SysRole> list = sysRoleMapper.selectByExample(example);
		return list.size() > 0;
	}

	/**
	 * 
	 * @Title: selectList
	 * 
	 * @Description: 根据分类编号查询角色列表
	 * 
	 * @return List<SysRole>
	 */
	public List<SysRole> selectList(String typeCode) {
		Example example = new Example(SysRole.class);
		example.createCriteria().andEqualTo("typeCode", typeCode).andEqualTo("isDel", IConstant.YesOrNo.NO.toString());
		return sysRoleMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectByUsername
	 * 
	 * @Description: 根据角色编号查询角色信息
	 * 
	 * @param roleCode
	 * @param roleId
	 * @return SysRole
	 */
	public SysRole selectByRoleCode(String roleCode, Integer roleId) {
		Example example = new Example(SysRole.class);
		example.createCriteria().andEqualTo("roleCode", roleCode).andNotEqualTo("roleId", roleId);
		List<SysRole> list = sysRoleMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}

	public int insert(SysRole sysRole) {
		sysRole.setIsDel(IConstant.YesOrNo.NO.toString());
		sysRole.setCreateOn(StringTool.newDate());
		return sysRoleMapper.insertSelective(sysRole);
	}

}