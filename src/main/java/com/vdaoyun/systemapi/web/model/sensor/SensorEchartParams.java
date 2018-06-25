package com.vdaoyun.systemapi.web.model.sensor;

import java.io.Serializable;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel("探测器统计图请求参数结构体")
public class SensorEchartParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "塘口编号，查询塘口下所有探测器运行轨迹图时使用")
	private Long pondsId;
	
	@ApiModelProperty(value = "设备编号，查询设备下所有探测器运行轨迹图时使用")
	private String terminalId;
	
	@ApiModelProperty(value = "时间段，单位:天，默认：1，即最近1天。建议数值不要过大，数值过大，查询速度慢，响应慢，影响体验感")
	private Integer expr;

	public Long getPondsId() {
		return pondsId;
	}

	public void setPondsId(Long pondsId) {
		this.pondsId = pondsId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public Integer getExpr() {
		return expr;
	}

	public void setExpr(Integer expr) {
		this.expr = expr;
	}
	
}
