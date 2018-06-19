package com.vdaoyun.systemapi.mq.model;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @Package com.vdaoyun.systemapi.mq.model
 *  
 * @ClassName: MQSensorRecordModel
 *  
 * @Description: 传感器运行记录
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月19日 上午10:45:26
 *
 */
public class MQSensorRecordModel extends MQAbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据采集频率
	 */
	private Integer SampeFrequency;
	
	/**
	 * 运行数据
	 */
	private List<HashMap<String, Object>> data;

	public Integer getSampeFrequency() {
		return SampeFrequency;
	}

	public void setSampeFrequency(Integer sampeFrequency) {
		SampeFrequency = sampeFrequency;
	}

	public List<HashMap<String, Object>> getData() {
		return data;
	}

	public void setData(List<HashMap<String, Object>> data) {
		this.data = data;
	}

}
