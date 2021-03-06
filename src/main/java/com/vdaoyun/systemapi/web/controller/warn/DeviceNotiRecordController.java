package com.vdaoyun.systemapi.web.controller.warn;

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

import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.web.model.warn.DeviceNotiRecord;
import com.vdaoyun.systemapi.web.service.warn.DeviceNotiRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "异常通知记录")
@RestController
@RequestMapping(value = "/warn/noti")
public class DeviceNotiRecordController {
	
	@Autowired
	private DeviceNotiRecordService service;
	
	@ApiOperation(value = "列表查询", hidden = true)
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
			@RequestBody DeviceNotiRecord entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
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
	
	@ApiOperation(value = "新增", hidden = true)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public AjaxJson insert(
		@RequestBody @Valid @ApiParam(value = "DeviceNotiRecord") DeviceNotiRecord entity,
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
		ajaxJson.setMsg(result ? "新增成功" : "新增失败");
		return ajaxJson;

	}
	
	@ApiOperation(value = "编辑", hidden = true)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody @ApiParam(value = "异常通知记录") DeviceNotiRecord entity, 
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
	
	@GetMapping("read")
	@ApiOperation(value = "报警已读", tags = {"A小程序_____首页_塘口详情_将报警通知设置为已读状态"})
	public AjaxJson read(@RequestParam("pondsId") @ApiParam("塘口编号") Long pondsId) throws Exception {
		service.read(pondsId);
		return new AjaxJson();
	}
	
	@GetMapping("wx/noti")
	@ApiOperation(value = "微信通知用户", hidden = true)
	public AjaxJson wxNoti(@RequestParam("id") Long id) throws Exception {
		service.wxNoti(id);
		return new AjaxJson();
	}
	
}
