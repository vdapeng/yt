package com.vdaoyun.systemapi.web.model.sensor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "传感器运行记录")
@Table(name = "sensor_record")
public class SensorRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(name = "id", value = "", hidden = true )
    private Long id;
	/**
     * 设备编号
     */
    @NotNull(message = "设备编号 is required")
    @Column(name = "terminal_id")
    @ApiModelProperty(name = "terminal_id", value = "设备编号" )
    private String terminalId;
	/**
     * 推送时间
     */
    @NotNull(message = "推送时间 is required")
    @Column(name = "post_time")
    @ApiModelProperty(name = "postTime", value = "推送时间" )
    private Date postTime;
	/**
     * 检测值
     */
    @NotNull(message = "检测值 is required")
    @Column(name = "value")
    @ApiModelProperty(name = "value", value = "检测值" )
    private String value;
    /**
     * 温度
     */
    @Column(name = "temperature_value")
    @ApiModelProperty(name = "temperatureValue", value = "传感器温度值" )
    private String temperatureValue;
    
	/**
     * 监测时间
     */
    @NotNull(message = "监测时间 is required")
    @Column(name = "data_time")
    @ApiModelProperty(name = "dataTime", value = "监测时间" )
    private Date dataTime;
	/**
     * 传感器编码
     */
    @Column(name = "code")
    @ApiModelProperty(name = "code", value = "传感器编码" )
    private String code;
    
  	/**
     * 获取
     *
     * @return remark - 
     */
    public Long getId() {
        return id;
    }

	/**
     * 设置
     *
     * @param remark 
     */
    public void setId(Long id) {
        this.id = id;
    }
  	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
     * 获取推送时间
     *
     * @return remark - 推送时间
     */
    public Date getPostTime() {
        return postTime;
    }

	/**
     * 设置推送时间
     *
     * @param remark 推送时间
     */
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
  	/**
     * 获取检测值
     *
     * @return remark - 检测值
     */
    public String getValue() {
        return value;
    }

	/**
     * 设置检测值
     *
     * @param remark 检测值
     */
    public void setValue(String value) {
        this.value = value;
    }
  	/**
     * 获取监测时间
     *
     * @return remark - 监测时间
     */
    public Date getDataTime() {
        return dataTime;
    }

	/**
     * 设置监测时间
     *
     * @param remark 监测时间
     */
    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }
  	/**
     * 获取传感器编码
     *
     * @return remark - 传感器编码
     */
    public String getCode() {
        return code;
    }

	/**
     * 设置传感器编码
     *
     * @param remark 传感器编码
     */
    public void setCode(String code) {
        this.code = code;
    }

	public String getTemperatureValue() {
		return temperatureValue;
	}

	public void setTemperatureValue(String temperatureValue) {
		this.temperatureValue = temperatureValue;
	}
    
    
}