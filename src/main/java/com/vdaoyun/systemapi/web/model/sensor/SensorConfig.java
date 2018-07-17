package com.vdaoyun.systemapi.web.model.sensor;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "探测器配置，用于批量配置塘口探测器")
public class SensorConfig implements Serializable {
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 1L;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String code;
	
	private String isEnable = "n";
	
	private String name;
	
	

}
