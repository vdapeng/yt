/**     
    * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.user.service;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdaoyun.common.api.base.service.BaseService;
import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.common.api.util.MD5Util;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.base.function.service.SysFunctionService;
import com.vdaoyun.systemapi.web.base.role.model.SysRole;
import com.vdaoyun.systemapi.web.base.role.service.SysRoleService;
import com.vdaoyun.systemapi.web.base.role.service.SysRoleUserService;
import com.vdaoyun.systemapi.web.base.user.dao.SysUserMapper;
import com.vdaoyun.systemapi.web.base.user.model.SysUser;
import com.vdaoyun.util.StringTool;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.user.service
 * 
 * @ClassName: SysUserService
 * 
 * @Description: 
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:16:51
 */
@Service
public class SysUserService extends BaseService<SysUser> {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleUserService sysRoleUserService;
	@Autowired
	private SysFunctionService sysFunctionService;

	
	public SysUser loadUserByUsername(String username) {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("username", username)
		.andEqualTo("isDel", IConstant.YesOrNo.NO.toString());
		List<SysUser> list = sysUserMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 
	 * @Title: login
	 *  
	 * @Description: 登录
	 *  
	 * @param username
	 * @param password
	 * @return AjaxJson
	 */
	public AjaxJson login(String username, String password){
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		SysUser sysUser = selectByUsername(username, null);
		if(sysUser == null){
			j.setMsg("用户名不存在");
		} else if(!sysUser.getPassword().equals(MD5Util.MD5Encode(password, null))){
			j.setMsg("登录密码错误");
		} else if(sysUser.getStatus().equals(IConstant.NormalOrDisable.DISABLE.toString())){
			j.setMsg("账户已被禁用");
		} else {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			SysRole sysRole = sysRoleService.selectByUserId(sysUser.getUserId());
			hashMap.put("sysUser", sysUser);
			hashMap.put("sysRole", sysRole);
			hashMap.put("functionList", sysFunctionService.selectList(sysRole.getRoleId()));
			j.setData(hashMap);
			j.setSuccess(true);
			j.setMsg("登录成功");
		}
		return j;
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @param password 原密码
	 * @param newPassword新密码
	 * @return
	 */
	public AjaxJson updatePwd(Integer id,String password, String newPassword){
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
		if(sysUser == null){
			j.setMsg("用户名不存在");
		} else if(!sysUser.getPassword().equals(MD5Util.MD5Encode(password, null))){
			j.setMsg("登录密码错误");
		} else if(sysUser.getStatus().equals(IConstant.NormalOrDisable.DISABLE.toString())){
			j.setMsg("账户已被禁用");
		} else {//修改密码
			sysUser.setPassword(MD5Util.MD5Encode(newPassword, null));
			sysUserMapper.updateByPrimaryKeySelective(sysUser);
			j.setSuccess(true);
			j.setMsg("密码修改成功");
		}
		return j;
	}
	
	
	/**
	 * 忘记密码
	 * @param code
	 * @param password
	 * @return
	 */
	public AjaxJson forgetPwd(String mobile, String password){
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		//通过手机号查询用户信息
		Example example = new Example(SysUser.class);
		example.createCriteria()
		.andEqualTo("mobile", mobile);
		List<SysUser>list=sysUserMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			SysUser sysUser=list.get(0);
			sysUser.setPassword(MD5Util.MD5Encode(password, null));
			sysUserMapper.updateByPrimaryKeySelective(sysUser);
			j.setSuccess(true);
			j.setMsg("密码设置成功");
		}else{
			j.setMsg("没有该用户信息");
		}
		return j;
	}
	
	
	/**
	 * 
	 * @Title: add
	 *  
	 * @Description: 新增用户信息
	 *  
	 * @param sysUser
	 * @return AjaxJson
	 */
	public AjaxJson add(SysUser sysUser){
		AjaxJson j = new AjaxJson();
		if(selectByUsername(sysUser.getUsername(), null) != null){
			j.setSuccess(false);
			j.setMsg("用户名已存在");
		} else {
			// 新增用户信息
			sysUser.setPassword(MD5Util.MD5Encode(sysUser.getPassword(), null));
			insert(sysUser);
			// 新增角色用户信息
			sysRoleUserService.insert(sysUser.getRoleId(), sysUser.getUserId());
			j.setMsg("新增成功");
		}
		return j;
	}
	
	/**
	 * 
	 * @Title: selectByUsername
	 *  
	 * @Description: 根据用户名查询用户信息
	 *  
	 * @param username
	 * @return SysUser
	 */
	public SysUser selectByUsername(String username, Integer userId){
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("username", username)
		.andNotEqualTo("userId", userId)
		.andEqualTo("isDel", IConstant.YesOrNo.NO.toString());
		List<SysUser> list = sysUserMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public int insert(SysUser sysUser){
		sysUser.setIsDel(IConstant.YesOrNo.NO.toString());
		sysUser.setStatus(IConstant.NormalOrDisable.NORMAL.toString());
		sysUser.setCreateOn(StringTool.newDate());
		return sysUserMapper.insertSelective(sysUser);
	}

}