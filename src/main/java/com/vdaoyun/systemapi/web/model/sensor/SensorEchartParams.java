package com.vdaoyun.systemapi.web.model.sensor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("探测器统计图请求参数结构体")
public class SensorEchartParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "塘口编号不可以为空")
	@ApiModelProperty(value = "塘口编号，查询塘口下所有探测器运行轨迹图时使用")
	private Long pondsId;
	
	@NotNull(message = "设备编号不可以为空")
	@ApiModelProperty(value = "设备编号，查询设备下所有探测器运行轨迹图时使用")
	private String terminalId;
	
	@ApiModelProperty(value = "探测器编号，选填")
	public String sensorCode;
	
	@ApiModelProperty(value = "时间段，单位:天，默认：1，即最近1天。建议数值不要过大，数值过大，查询速度慢，响应慢，影响体验感。优先级最高")
	private Integer expr = 1;

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

	public String getSensorCode() {
		return sensorCode;
	}

	public void setSensorCode(String sensorCode) {
		this.sensorCode = sensorCode;
	}
	
	@Transient
	@NotNull(message = "开始时间不可以为空")
    @ApiModelProperty(value = "开始时间，优先级次于expr")
    private Date beginDate;
    
    @Transient
    @NotNull(message = "结束时间不可以为空")
    @ApiModelProperty(value = "结束时间，优先级次于expr")
    private Date finishDate;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
}
