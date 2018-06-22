/**     
 * 版权所有：2016 vdaoyun.com 武汉微道云信息科技有限公司 
 */
package com.vdaoyun.systemapi.web.base.role.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
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
import com.vdaoyun.systemapi.web.base.function.service.SysFunctionService;
import com.vdaoyun.systemapi.web.base.role.model.SysRole;
import com.vdaoyun.systemapi.web.base.role.service.SysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @Package com.vdaoyun.systemweb.web.system.role.controller
 * 
 * @ClassName: SysRoleController
 * 
 * @Description:
 * 
 * @author MingJie (limingjie@vdaoyun.com)
 * 
 * @date 2017-3-30 21:05:17
 */
@Api(tags = { "角色相关接口" })
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

	private static final Logger log = LoggerFactory.getLogger(SysRoleController.class);

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysFunctionService sysFunctionService;

	@ApiOperation("查询列表")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(defaultValue = "1") @ApiParam(value = "页码", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") @ApiParam(value = "每页条数", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "orderby") @ApiParam(value = "排序字段", required = true, defaultValue = "orderby") String sortby,
			@RequestParam(defaultValue = "desc") @ApiParam(value = "排序方式", required = true, defaultValue = "desc") String order,
			@RequestBody @ApiParam(value = "搜索条件", required = false) HashMap<String, Object> param) throws Exception {
		AjaxJson j = new AjaxJson();
		PageHelper.startPage(pageNum, pageSize);
		param.put("orderByClause", sortby + " " + order);
		param.put("isDel", IConstant.YesOrNo.NO.toString());
		// 列表返回数据
		j.setData(new PageInfo<HashMap<String, Object>>(sysRoleService.selectTableList(param)));
		return j;
	}

	@ApiOperation("新增")
	@RequestMapping(method = RequestMethod.POST)
	public AjaxJson insert(@RequestBody @ApiParam(value = "角色信息", required = true) @Valid SysRole sysRole,
			Errors errors) throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		j = sysRoleService.add(sysRole);
		log.info("sys_role表新增数据");

		return j;
	}

	@ApiOperation("更新")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(@PathVariable @ApiParam(value = "角色编号", required = true) Integer id,
			@RequestBody @ApiParam(value = "角色信息", required = true) @Valid SysRole sysRole, Errors errors)
			throws Exception {
		AjaxJson j = new AjaxJson();
		if (errors.hasErrors()) {
			j.setSuccess(false);
			j.setMsg(errors.getAllErrors().get(0).getDefaultMessage());
			return j;
		}
		sysRole.setCreateOn(null);
		sysRoleService.update(sysRole);
		j.setMsg("更新成功");
		log.info("sys_role表更新数据，roleId：" + id);

		return j;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(@RequestParam @ApiParam(value = "角色编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();

		SysRole sysRole = new SysRole();
		sysRole.setRoleId(id);
		sysRole.setIsDel(IConstant.YesOrNo.YES.toString());
		sysRoleService.update(sysRole);
		j.setMsg("删除成功");
		log.info("sys_role表删除数据，roleId：" + id);

		return j;
	}

	@ApiOperation("查询角色菜单")
	@RequestMapping(value = "/{id}/function", method = RequestMethod.GET)
	public AjaxJson selectList(@PathVariable @ApiParam(value = "角色编号", required = true) Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(sysFunctionService.selectList(id));
		return j;
	}

	@ApiOperation("查询角色菜单权限列表")
	@RequestMapping(value = "/{id}/privilege", method = RequestMethod.GET)
	public AjaxJson selectPrivilege(@PathVariable @ApiParam(value = "角色编号", required = true) Integer id,
			@RequestParam("typeCode") @ApiParam(value = "类型编码", required = true) String typeCode) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setData(sysFunctionService.selectPrivilege(id, typeCode));
		return j;
	}

	@ApiOperation("保存权限")
	@RequestMapping(value = "/{id}/privilege", method = RequestMethod.POST)
	public AjaxJson savePrivilege(
			@RequestBody @ApiParam(value = "权限信息", required = true) HashMap<String, Object> hashMap,
			@PathVariable Integer id) throws Exception {
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		String menu = hashMap.get("menu").toString();
		String operation = hashMap.get("operation").toString();

		if (id == null) {
			j.setMsg("角色编号为空");
		} else if (StringUtils.isEmpty(menu)) {
			j.setMsg("菜单编号为空");
		} else if (StringUtils.isEmpty(operation)) {
			j.setMsg("操作编号为空");
		} else {
			sysRoleService.savePrivilege(id, menu, operation);
			j.setMsg("保存成功");
			j.setSuccess(true);
		}
		return j;
	}

	@ApiOperation("验证唯一性")
	@RequestMapping(value = "/verifyOnly", method = RequestMethod.GET)
	public boolean verifyOnly(HttpServletRequest request, @RequestParam @ApiParam(value = "角色编码", required = true) String value,
			@RequestParam(required = false) @ApiParam(value = "角色编号", required = false) Integer roleId) throws Exception {
		boolean b = false;
		if (StringUtils.isNotEmpty(value)) {
			b = sysRoleService.selectByRoleCode(value, roleId) != null;
		}
		return b;
	}
}