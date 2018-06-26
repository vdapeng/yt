package com.vdaoyun.systemapi.web.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.model.user.User;
import com.vdaoyun.systemapi.web.service.user.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "用户")
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@ApiOperation(value = "列表查询")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
			@RequestBody User entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键查询详情")
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long", paramType = "path")	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AjaxJson getById(
		@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectByPrimaryKey(id));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"H后台管理____用户管理_用户激活/用户禁用"}, value ="")
	@GetMapping(value = "/enable/{id}")
	public AjaxJson enable(@PathVariable(value = "id") Long id) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		User user = service.selectByPrimaryKey(id);
		Boolean result = false;
		if (user == null) {
			ajaxJson.setMsg("用户信息不存在");
		} else if (user.getIsEnable().equals(YesOrNo.NO.toString())) {
			result = service.enable(id);
		} else {
			result = service.disEnable(id);
		}
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "操作成功" : "操作失败");
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_查询用户信息"},value = "新增")
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public AjaxJson selectInfo(@RequestParam(value = "openid") @ApiParam("微信openid") String openid) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectInfoByOpenid(openid));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____注册_新增用户"},value = "新增")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public AjaxJson insert(
		@RequestBody @Valid @ApiParam(value = "User") User entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
		} else if (service.isExit(entity.getOpenid(), entity.getMobile())) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("请勿重复注册");
		} else {
			Boolean result = service.insertInfo(entity) > 0;
			ajaxJson.setData(entity);
			ajaxJson.setSuccess(result);
			ajaxJson.setMsg(result ? "新增成功" : "新增失败");
		}
		return ajaxJson;

	}
	
	@ApiOperation(tags = {"A小程序_____我的_编辑用户信息"}, value = "编辑")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody @ApiParam(value = "用户") User entity, 
		@PathVariable(value = "id") @ApiParam(value = "主键") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		entity.setId(id);
		Boolean result = service.update(entity) > 0;
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "编辑成功" : "编辑失败");
		ajaxJson.setData(entity);
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键删除", hidden = true)
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(
		@PathVariable(value = "id") Integer id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		Boolean result = service.delete(id) > 0;
		ajaxJson.setMsg(result ? "删除成功" : "删除失败");
		ajaxJson.setSuccess(result);
		return ajaxJson;
	}

}
