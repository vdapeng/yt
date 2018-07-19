package com.vdaoyun.systemapi.web.controller.sensor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.api.enums.IConstant.YesOrNo;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.common.utils.AjaxJsonUtils;
import com.vdaoyun.systemapi.web.model.sensor.Sensor;
import com.vdaoyun.systemapi.web.model.sensor.SensorConfig;
import com.vdaoyun.systemapi.web.service.sensor.SensorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "传感器")
@RestController
@RequestMapping(value = "/sensor")
public class SensorController {
	
	@Autowired
	private SensorService service;
	
	@ApiOperation(value = "列表查询", hidden = true)
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "createDate", required = false) @ApiParam(value = "排序字段")  String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
			@RequestBody Sensor entity
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_通过终端编号查询该终端下所有探测器信息，包含所属塘口基本信息，用于通过终端查看探测器信息"}, value = "")
	@GetMapping("{terminalId}/list")
	public AjaxJson selectByTerminalId(
			@PathVariable("terminalId") String terminalId
	) throws Exception {
		return AjaxJsonUtils.ajaxJson(service.selectByTerminalId(terminalId));
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
		@RequestBody @Valid @ApiParam(value = "Sensor") Sensor entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
			return ajaxJson;
		}
		Boolean result = service.insertInfo(entity) > 0;
		ajaxJson.setData(entity);
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "新增成功" : "新增失败");
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_保存探测器"}, value = "新增")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public AjaxJson miniSave(
		@RequestBody @Valid @ApiParam(value = "Sensor") Sensor entity,
		BindingResult bindingResult
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
			return ajaxJson;
		}
		Sensor sensor = service.isExit(entity.getTerminalId(), entity.getCode(), entity.getPondsId());
		Boolean result = true;
		if (sensor != null && entity.getIsEnable().equalsIgnoreCase(YesOrNo.NO.toString())) {
			entity.setId(sensor.getId());
//			result = service.update(entity) > 0;
			result = service.delete(sensor.getId()) > 0;
		} else if (sensor == null && entity.getIsEnable().equalsIgnoreCase(YesOrNo.YES.toString())) {
			result = service.insertInfo(entity) > 0;
		} 
		ajaxJson.setData(entity);
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "操作成功" : "操作失败");
		return ajaxJson;
	}
	
	@ApiOperation(value = "批量新增", hidden = true)
	@RequestMapping(value = "batch", method = RequestMethod.POST)
	public AjaxJson batchInsert(@RequestBody @Valid List<Sensor> list, BindingResult bindingResult) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		if (bindingResult.hasErrors()) {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
			return ajaxJson;
		}
		Boolean result = service.batchInsert(list) > 0;
		ajaxJson.setSuccess(result);
		ajaxJson.setMsg(result ? "新增成功" : "新增失败");
		return ajaxJson;
	}
	
	@ApiOperation(value = "编辑", hidden = true)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public AjaxJson update(
		@RequestBody @ApiParam(value = "传感器") Sensor entity, 
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
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public AjaxJson delete(
		@PathVariable(value = "id") @ApiParam(value = "主键") Long id
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		Boolean result = service.delete(id) > 0;
		ajaxJson.setMsg(result ? "删除成功" : "删除失败");
		ajaxJson.setSuccess(result);
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_根据塘口编号查询探测器列表"}, value = "")
	@RequestMapping(value = "pid", method = RequestMethod.GET)
	public AjaxJson selectListByPondsId(@RequestParam("pondsId") Long pondsId) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectListByPondsId(pondsId));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_根据塘口编号和设备编号查询探测器列表"}, value = "")
	@GetMapping("ptid")
	public AjaxJson selectListByPondsIdForJson(@RequestParam Long pondsId, @RequestParam String terminalId) throws Exception {
		return AjaxJsonUtils.ajaxJson(service.selectListByPondsIdForJson(pondsId, terminalId));
	}
	
	@ApiOperation(value = "通过具体的探测器类型编号{code}和具体的设备编号{terminalId}，查找相关探测器列表。", hidden = true)
	@GetMapping("code")
	public AjaxJson selectListByCode(
			@RequestParam("code") @ApiParam("具体探测器类型编码。例如：HP") String code,
			@RequestParam("terminalId") @ApiParam("设备编号") String terminalId
	) throws Exception 	{
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectListByCode(code, terminalId));
		return ajaxJson;
	}
	
	@ApiOperation(tags = {"A小程序_____我的_终端管理_根据终端编号查询探测器列表"},  value = "通过总探测器类型编号{groupCode}和具体的设备编号{terminalId}，查找相关探测器列表。")
	@GetMapping("groupCode")
	public AjaxJson selectListByGroupCode(
			@RequestParam(name = "groupCode", required = false, defaultValue = "CGQ") @ApiParam("总探测器类型编码。例如：CGQ") String groupCode,
			@RequestParam("terminalId") @ApiParam("设备编号") String terminalId
	) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(service.selectListByGroupCode(groupCode, terminalId));
		return ajaxJson;
	}

	@ApiOperation(tags = {"A小程序_____我的_塘口管理_批量配置塘口探测器"},  value = "")
	@PutMapping("config")
	public AjaxJson batchConfig(
			@RequestParam String terminalId, @RequestParam Long pondsId, @RequestBody List<SensorConfig> sensorConfigs) throws Exception {
		service.batchConfig(terminalId, pondsId, sensorConfigs);
		return new AjaxJson();
	}
	
}
