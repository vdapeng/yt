package com.vdaoyun.systemapi.web.model.device;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "设备运行记录")
@Table(name = "device_record")
public class DeviceRecord implements Serializable {

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
    @ApiModelProperty(name = "terminalId", value = "设备编号" )
    private String terminalId;
	/**
     * 推送时间
     */
    @Column(name = "post_time")
    @ApiModelProperty(name = "postTime", value = "推送时间" )
    private Date postTime;
	/**
     * 太阳能电压
     */
    @Column(name = "solar_voltage")
    @ApiModelProperty(name = "solarVoltage", value = "太阳能电压" )
    private String solarVoltage;
	/**
     * 电池电压
     */
    @Column(name = "battery_voltage")
    @ApiModelProperty(name = "batteryVoltage", value = "电池电压" )
    private String batteryVoltage;
	/**
     * 电池温度
     */
    @Column(name = "battery_temperature")
    @ApiModelProperty(name = "batteryTemperature", value = "电池温度" )
    private String batteryTemperature;
	/**
     * 系统温度
     */
    @Column(name = "system_temperature")
    @ApiModelProperty(name = "systemTemperature", value = "系统温度" )
    private String systemTemperature;
	/**
     * 电池电量
     */
    @Column(name = "battery_level")
    @ApiModelProperty(name = "batteryLevel", value = "电池电量" )
    private String batteryLevel;
	/**
     * 充电池状态
     */
    @Column(name = "charger_status")
    @ApiModelProperty(name = "chargerStatus", value = "充电池状态" )
    private String chargerStatus;
	/**
     * 表面温度
     */
    @Column(name = "shell_temperature")
    @ApiModelProperty(name = "shellTemperature", value = "表面温度" )
    private String shellTemperature;
	/**
     * GPD数据
     */
    @Column(name = "gps")
    @ApiModelProperty(name = "gps", value = "GPD数据" )
    private String gps;
	/**
     * 开机时间
     */
    @Column(name = "power_key")
    @ApiModelProperty(name = "powerKey", value = "开机时间" )
    private String powerKey;
    
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
  	/**
     * 获取设备编号
     *
     * @return remark - 设备编号
     */
    public String getTerminalId() {
        return terminalId;
    }

	/**
     * 设置设备编号
     *
     * @param remark 设备编号
     */
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
     * 获取太阳能电压
     *
     * @return remark - 太阳能电压
     */
    public String getSolarVoltage() {
        return solarVoltage;
    }

	/**
     * 设置太阳能电压
     *
     * @param remark 太阳能电压
     */
    public void setSolarVoltage(String solarVoltage) {
        this.solarVoltage = solarVoltage;
    }
  	/**
     * 获取电池电压
     *
     * @return remark - 电池电压
     */
    public String getBatteryVoltage() {
        return batteryVoltage;
    }

	/**
     * 设置电池电压
     *
     * @param remark 电池电压
     */
    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }
  	/**
     * 获取电池温度
     *
     * @return remark - 电池温度
     */
    public String getBatteryTemperature() {
        return batteryTemperature;
    }

	/**
     * 设置电池温度
     *
     * @param remark 电池温度
     */
    public void setBatteryTemperature(String batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }
  	/**
     * 获取系统温度
     *
     * @return remark - 系统温度
     */
    public String getSystemTemperature() {
        return systemTemperature;
    }

	/**
     * 设置系统温度
     *
     * @param remark 系统温度
     */
    public void setSystemTemperature(String systemTemperature) {
        this.systemTemperature = systemTemperature;
    }
  	/**
     * 获取电池电量
     *
     * @return remark - 电池电量
     */
    public String getBatteryLevel() {
        return batteryLevel;
    }

	/**
     * 设置电池电量
     *
     * @param remark 电池电量
     */
    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
  	/**
     * 获取充电池状态
     *
     * @return remark - 充电池状态
     */
    public String getChargerStatus() {
        return chargerStatus;
    }

	/**
     * 设置充电池状态
     *
     * @param remark 充电池状态
     */
    public void setChargerStatus(String chargerStatus) {
        this.chargerStatus = chargerStatus;
    }
  	/**
     * 获取表面温度
     *
     * @return remark - 表面温度
     */
    public String getShellTemperature() {
        return shellTemperature;
    }

	/**
     * 设置表面温度
     *
     * @param remark 表面温度
     */
    public void setShellTemperature(String shellTemperature) {
        this.shellTemperature = shellTemperature;
    }
  	/**
     * 获取GPD数据
     *
     * @return remark - GPD数据
     */
    public String getGps() {
        return gps;
    }

	/**
     * 设置GPD数据
     *
     * @param remark GPD数据
     */
    public void setGps(String gps) {
        this.gps = gps;
    }
  	/**
     * 获取开机时间
     *
     * @return remark - 开机时间
     */
    public String getPowerKey() {
        return powerKey;
    }

	/**
     * 设置开机时间
     *
     * @param remark 开机时间
     */
    public void setPowerKey(String powerKey) {
        this.powerKey = powerKey;
    }
    
    
}