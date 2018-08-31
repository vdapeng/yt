package com.vdaoyun.systemapi.web.controller.ponds;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.model.ponds.PondsShareRecord;
import com.vdaoyun.systemapi.web.service.ponds.PondsExService;
import com.vdaoyun.systemapi.web.service.ponds.PondsShareRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "鱼塘分享记录")
@RestController
@RequestMapping(value = "/ponds/share")
public class PondsShareRecordController {
	
	@Autowired
	private PondsShareRecordService service;
	@Autowired
	private PondsExService pondsExService;
	
	@ApiOperation(value = "列表查询", hidden = true)
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
			@RequestBody PondsShareRecord entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfoEx(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键查询详情", hidden = true)
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long", paramType = "path")	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AjaxJson getById(
		@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectByPrimaryKey(id));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____首页_共享塘口"}, value = "新增")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public AjaxJson insert(
		@RequestBody @ApiParam("实体") @Valid PondsShareRecord entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		Boolean result = service.insertInfo(entity) > 0;
		ajaxJson.setData(entity);
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "操作成功" : "操作失败");
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____首页_共享塘口"}, value = "新增")
	@RequestMapping(value = "record", method = RequestMethod.POST)
	public AjaxJson insertRecord(
		@RequestBody @ApiParam("实体") HashMap<String, Object> entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		Boolean result = true;
		if (result) {
			pondsExService.version(null);
		}
		ajaxJson.setData(entity);
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "操作成功" : "操作失败");
		return ajaxJson;
	}
	
	@ApiOperation(value = "编辑", hidden = true)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody @ApiParam(value = "鱼塘分享记录") PondsShareRecord entity, 
		@PathVariable(value = "id") @ApiParam(value = "主键") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		entity.setId(id);
		Boolean result = service.update(entity) > 0;
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "操作成功" : "操作失败");
		ajaxJson.setData(entity);
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____共享_取消共享塘口/取消关注塘口"}, value = "通过主键删除")
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Integer", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(
		@PathVariable(value = "id") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		Boolean result = service.delete(id) > 0;
		ajaxJson.setMsg(result ? "操作成功" : "操作失败");
		ajaxJson.setSuccess(result);
		if (result) {
			pondsExService.version(null);
		}
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____共享_我分享的塘口"}, value = "我分享的塘口")
	@RequestMapping(value = "{openId}/share", method = RequestMethod.GET)
	public AjaxJson myShare(
		@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
		@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
		@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
		@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
		@PathVariable("openId") String openId
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		PondsShareRecord entity = new PondsShareRecord();
		entity.setShareOpenid(openId);
		ajaxJson.setData(service.selectPageInfoEx(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	@ApiOperation(tags = {"A小程序_____共享_分享给我的塘口"}, value = "分享给我的塘口")
	@RequestMapping(value = "/share/{openId}", method = RequestMethod.GET)
	public AjaxJson shareMe(
		@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
		@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
		@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
		@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
		@PathVariable("openId") String openId
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		PondsShareRecord entity = new PondsShareRecord();
		entity.setOpenid(openId);
		ajaxJson.setData(service.selectPageInfoEx(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}

}
