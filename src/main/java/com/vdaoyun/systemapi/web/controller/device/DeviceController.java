package com.vdaoyun.systemapi.web.controller.device;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.systemapi.web.model.device.Device;
import com.vdaoyun.systemapi.web.service.device.DeviceService;

import com.vdaoyun.common.bean.AjaxJson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "设备信息")
@RestController
@RequestMapping(value = "/device")
public class DeviceController {
	
	@Autowired
	private DeviceService service;
	
	@ApiOperation(value = "列表查询")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
			@RequestBody Device entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键查询详情")
	@RequestMapping(value = "/{terminalId}", method = RequestMethod.GET)
	public AjaxJson getById(
		@PathVariable(value = "terminalId") @ApiParam(value = "设备编号") String id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectByPrimaryKey(id));
		return ajaxJson;
	}
	
	@ApiOperation(value = "新增")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public AjaxJson insert(
		@RequestBody @Valid @ApiParam(value = "Device") Device entity,
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
	
	@ApiOperation(value = "编辑")
	@RequestMapping(value = "/{terminalId}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody @ApiParam(value = "设备") Device entity, 
		@PathVariable(value = "terminalId") @ApiParam(value = "设备编号") String terminalId
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		entity.setTerminalId(terminalId);
		Boolean result = service.update(entity) > 0;
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "编辑成功" : "编辑失败");
		ajaxJson.setData(entity);
		return ajaxJson;
	}
	
	@ApiOperation(value = "通过主键删除")
	@RequestMapping(value = "/{terminalId}", method = RequestMethod.DELETE)
	public AjaxJson delete(
		@PathVariable(value = "terminalId") @ApiParam(value = "设备编号") String terminalId
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		Boolean result = service.delete(terminalId) > 0;
		ajaxJson.setMsg(result ? "删除成功" : "删除失败");
		ajaxJson.setSuccess(result);
		return ajaxJson;
	}

}
