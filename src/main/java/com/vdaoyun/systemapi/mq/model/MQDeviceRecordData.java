package com.vdaoyun.systemapi.mq.model;

import java.io.Serializable;

public class MQDeviceRecordData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double SolarVoltage;
	private double SV;
	private double BatteryVoltage;
	private double BV;
	private double BatteryTemperature;
	private double BT;
	private double SystemTemperature;
	private double SystemT;
	private double BatteryLevel;
	private double BL;
	private double ShellTemperature;
	private double ShellT;
	private String GPS;
	private String PowerKey;
	private String PK;
	private Integer ChargerStatus;
	private Integer CS;
	
	public double getSV() {
		return SV;
	}
	public void setSV(double sV) {
		SV = sV;
	}
	public double getBV() {
		return BV;
	}
	public void setBV(double bV) {
		BV = bV;
	}
	public double getBT() {
		return BT;
	}
	public void setBT(double bT) {
		BT = bT;
	}
	public double getSystemT() {
		return SystemT;
	}
	public void setSystemT(double systemT) {
		SystemT = systemT;
	}
	public double getBL() {
		return BL;
	}
	public void setBL(double bL) {
		BL = bL;
	}
	public double getShellT() {
		return ShellT;
	}
	public void setShellT(double shellT) {
		ShellT = shellT;
	}
	public String getPK() {
		return PK;
	}
	public void setPK(String pK) {
		PK = pK;
	}
	public Integer getCS() {
		return CS;
	}
	public void setCS(Integer cS) {
		CS = cS;
	}
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
