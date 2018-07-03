/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.user.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdaoyun.common.api.enums.IConstant;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.base.user.model.SysUser;
import com.vdaoyun.systemapi.web.base.user.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.user.controller
 * 
 * @ClassName: SysUserController
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 15:16:51
 */
@ApiIgnore
@Api(tags = { "用户信息相关接口" }, hidden = true)
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

	private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;

	@ApiOperation("登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AjaxJson login(@RequestBody @ApiParam(value = "登陆用户信息", required = true) SysUser sysUser) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		String username = sysUser.getUsername();
		String password = sysUser.getPassword();

		if (StringUtils.isEmpty(username)) {
			j.setMsg("用户名为空");
		} else if (StringUtils.isEmpty(password)) {
			j.setMsg("登录密码为空");
		} else {
			j = sysUserService.login(username, password);
		}
		return j;
	}
	
	
	@ApiOperation("修改密码")
	@RequestMapping(value = "/updatePwd/{id}", method = RequestMethod.PUT)
	public AjaxJson updatePwd(@PathVariable Integer id, @RequestParam("password")String password,@RequestParam("newPassword")String newPassword) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		if (StringUtils.isEmpty(password)) {
			j.setMsg("登录密码为空");
		} else if (StringUtils.isEmpty(newPassword)) {
			j.setMsg("新密码为空");
		} else {
			j = sysUserService.updatePwd(id,password, newPassword);
		}
		return j;
	}
	
	@ApiOperation("忘记密码")
	@RequestMapping(value = "/forgetPwd", method = RequestMethod.PUT)
	public AjaxJson forgetPwd(@RequestParam("password")String password,@RequestParam("mobile")String mobile) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		if (StringUtils.isEmpty(mobile)) {
			j.setMsg("手机号为空");
		} else if (StringUtils.isEmpty(password)) {
			j.setMsg("密码为空");
		} else {
			j = sysUserService.forgetPwd(mobile,password);
		}
		return j;
	}
	
	@ApiOperation("查询列表")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(defaultValue = "1") @ApiParam(value = "页码", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") @ApiParam(value = "每页条数", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "orderno") @ApiParam(value = "排序字段", required = true, defaultValue = "orderno") String sortby,
			@RequestParam(defaultValue = "desc") @ApiParam(value = "排序方式", required = true, defaultValue = "desc") String order,
			@RequestBody @ApiParam(value = "搜索条件", required = false) HashMap<String, Object> param) throws Exception {
		AjaxJson j = new AjaxJson();
		PageHelper.startPage(pageNum, pageSize);
		param.put("orderByClause", sortby + " " + order);
		param.put("isDel", IConstant.YesOrNo.NO.toString());
		param.put("status", IConstant.NormalOrDisable.NORMAL.toString());
		// 列表返回数据
		j.setData(new PageInfo<HashMap<String, Object>>(sysUserService.selectTableList(param)));
		return j;
	}

	@ApiOperation("新增")
	@RequestMapping(method = RequestMethod.POST)
	public AjaxJson insert(@RequestBody @ApiParam(value = "用户信息", required = true) @Valid SysUser sysUser,
			Errors errors) throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		j = sysUserService.add(sysUser);
		log.info("sys_user表新增数据");
		return j;
	}

	@ApiOperation("更新")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(@PathVariable("id") @ApiParam(value = "用户编号", required = true) Integer id,
			@RequestBody @ApiParam(value = "用户信息", required = true) @Valid SysUser sysUser, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		sysUserService.update(sysUser);
		j.setMsg("更新成功");
		log.info("sys_user表更新数据，userId：" + id);
		return j;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(@PathVariable @ApiParam(value = "用户编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		SysUser sysUser = new SysUser();
		sysUser.setUserId(id);
		sysUser.setIsDel(IConstant.YesOrNo.YES.toString());
		sysUserService.update(sysUser);
		j.setMsg("删除成功");
		log.info("sys_user表删除数据，userId：" + id);
		return j;
	}

	@ApiOperation("验证唯一性")
	@RequestMapping(value = "/verifyOnly", method = RequestMethod.GET)
	public boolean verifyOnly(@RequestParam @ApiParam(value = "用户名", required = true) String value,
			@RequestParam(required = false) @ApiParam(value = "用户编号", required = true) Integer userId)
			throws Exception {
		boolean b = false;
		if (StringUtils.isNotEmpty(value)) {
			b = sysUserService.selectByUsername(value, userId) != null;
		}
		return b;
	}
}