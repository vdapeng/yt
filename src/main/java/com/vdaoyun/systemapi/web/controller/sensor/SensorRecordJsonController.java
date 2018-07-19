package com.vdaoyun.systemapi.web.controller.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.common.utils.AjaxJsonUtils;
import com.vdaoyun.systemapi.web.model.sensor.SensorEchartParams;
import com.vdaoyun.systemapi.web.model.sensor.SensorRecordJson;
import com.vdaoyun.systemapi.web.service.sensor.SensorRecordJsonService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/sensor/record/json")
public class SensorRecordJsonController {
	
	@Autowired
	private SensorRecordJsonService service;

	@ApiOperation(tags = {"A小程序_____首页_塘口详情_塘口24小时折线图"}, value = "")
	@PostMapping("echart")
	public AjaxJson selectEchartData(@RequestBody SensorEchartParams params) throws Exception {
		return AjaxJsonUtils.ajaxJson(JSON.parse(JSON.toJSONString(service.selectEchartData(params))));
	}

	@PostMapping("echart/device")
	public AjaxJson selectDeviceEchartData(@RequestBody SensorEchartParams params) throws Exception {
		return AjaxJsonUtils.ajaxJson(JSON.parse(JSON.toJSONString(service.selectDeviceEchartData(params))));
	}
	
	@ApiOperation(value = "列表查询", hidden = true)
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public AjaxJson select(
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) @ApiParam(value = "页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) @ApiParam(value = "每页条数") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "post_time", required = false) @ApiParam(value = "排序字段")  String order,
			@RequestParam(value = "sort", defaultValue = "DESC", required = false) @ApiParam(value = "排序方式") String sort,
			@RequestBody SensorRecordJson entity
	) throws Exception {
		return AjaxJsonUtils.ajaxJson(service.selectPageInfo(entity, pageNum, pageSize, order, sort));
	}
}
