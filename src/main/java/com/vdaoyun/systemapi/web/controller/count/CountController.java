package com.vdaoyun.systemapi.web.controller.count;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.common.utils.AjaxJsonUtils;
import com.vdaoyun.systemapi.web.service.count.CountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "统计信息")
@RestController
@RequestMapping(value = "/count")
public class CountController {
	
	@Autowired
	private CountService countService;

	@GetMapping("")
	@ApiOperation(hidden = true, value = "")
	public AjaxJson countNum() throws Exception {
		return AjaxJsonUtils.ajaxJson(countService.count());
	}
}
