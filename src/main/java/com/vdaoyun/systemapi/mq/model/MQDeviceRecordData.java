package com.vdaoyun.systemapi.mq.model;

import java.io.Serializable;

public class MQDeviceRecordData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double SolarVoltage;
	private double BatteryVoltage;
	private double BatteryTemperature;
	private double SystemTemperature;
	private double BatteryLevel;
	private double ShellTemperature;
	private String GPS;
	private String PowerKey;
	private Integer ChargerStatus;
	public double getSolarVoltage() {
		return SolarVoltage;
	}
	public void setSolarVoltage(double solarVoltage) {
		SolarVoltage = solarVoltage;
	}
	public double getBatteryVoltage() {
		return BatteryVoltage;
	}
	public void setBatteryVoltage(double batteryVoltage) {
		BatteryVoltage = batteryVoltage;
	}
	public double getBatteryTemperature() {
		return BatteryTemperature;
	}
	public void setBatteryTemperature(double batteryTemperature) {
		BatteryTemperature = batteryTemperature;
	}
	public double getSystemTemperature() {
		return SystemTemperature;
	}
	public void setSystemTemperature(double systemTemperature) {
		SystemTemperature = systemTemperature;
	}
	public double getBatteryLevel() {
		return BatteryLevel;
	}
	public void setBatteryLevel(double batteryLevel) {
		BatteryLevel = batteryLevel;
	}
	public double getShellTemperature() {
		return ShellTemperature;
	}
	public void setShellTemperature(double shellTemperature) {
		ShellTemperature = shellTemperature;
	}
	public String getGPS() {
		return GPS;
	}
	public void setGPS(String gPS) {
		GPS = gPS;
	}
	public String getPowerKey() {
		return PowerKey;
	}
	public void setPowerKey(String powerKey) {
		PowerKey = powerKey;
	}
	public Integer getChargerStatus() {
		return ChargerStatus;
	}
	public void setChargerStatus(Integer chargerStatus) {
		ChargerStatus = chargerStatus;
	}
	
}
