package com.vdaoyun.systemapi.mq.model;

/**
 * 
 * @Package com.vdaoyun.systemapi.mq.model
 *  
 * @ClassName: MQDeviceWarnModel
 *  
 * @Description: 设备报警模型
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月13日 上午10:20:33
 *
 */
public class MQDeviceWarnModel extends MQAbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 业务报警，目前有PH、DO、TEMPERATURE
	 */
	private String Alaram_Business;
	
	/**
	 * 设备报警，目前有BATTERYLOW、TEMPERATUREHIGH、CHARGERFAIL、SAFE
	 */
	private String Alaram_Equipment;

	public String getAlaram_Business() {
		return Alaram_Business;
	}

	public void setAlaram_Business(String alaram_Business) {
		Alaram_Business = alaram_Business;
	}

	public String getAlaram_Equipment() {
		return Alaram_Equipment;
	}

	public void setAlaram_Equipment(String alaram_Equipment) {
		Alaram_Equipment = alaram_Equipment;
	}

}
