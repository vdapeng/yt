package com.vdaoyun.systemapi.common.utils;

import com.vdaoyun.common.bean.AjaxJson;

public class AjaxJsonUtils {
	
	private static final int status = 0;
	private static final String msg = "操作成功";
	private static final Boolean success = true;
	
	public static AjaxJson ajaxJson(Object data) {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setData(data);
		ajaxJson.setStatus(status);
		ajaxJson.setMsg(msg);
		ajaxJson.setSuccess(success);
		return ajaxJson;
	}

}
