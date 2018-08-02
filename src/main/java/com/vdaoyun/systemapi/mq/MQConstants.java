package com.vdaoyun.systemapi.mq;

public class MQConstants {
	
	public static final String DEVICE_TOPIC = "/DEVICE/";	// 设备运行数据主题
	
	public static final String WARN_TOPIC = "/Alarm/";		// 报警主题
	
	public static final String CGQ_TOPIC = "/CGQ/"; 		// 传感器运行数据主题
	
	public static final String WARN_SEPARATOR = "&"; 			// 报警信息分隔符
	
	public static final String SUBEXPRESSION = "/+/BusinessData || /+/Alarm || /+/EquipmentData";
}
