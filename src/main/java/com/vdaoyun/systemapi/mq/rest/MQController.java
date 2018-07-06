package com.vdaoyun.systemapi.mq.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdaoyun.common.bean.AjaxJson;
import com.vdaoyun.systemapi.mq.MQProperty;
import com.vdaoyun.systemapi.mq.utils.Tools;

@RestController
@RequestMapping(value = "mq")
public class MQController {
	
	@Autowired
	private MQProperty mqProperty;
	
	@GetMapping("token")
	public AjaxJson applyToken() throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(Tools.applyToken(mqProperty.getSecretKey(), mqProperty.getAcessKey()));
		return ajaxJson;
	}

}
