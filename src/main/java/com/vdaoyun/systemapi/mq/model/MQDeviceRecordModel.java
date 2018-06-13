package com.vdaoyun.systemapi.mq.model;

import java.util.List;

/**
 * 
 * @Package com.vdaoyun.systemapi.mq.model
 *  
 * @ClassName: MQDeviceRecordModel
 *  
 * @Description: 设备运行记录模型
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年6月13日 上午10:19:53
 *
 */
public class MQDeviceRecordModel extends MQAbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer SampeFrequency;
	
	private List<MQDeviceRecordData> data;

	public Integer getSampeFrequency() {
		return SampeFrequency;
	}

	public void setSampeFrequency(Integer sampeFrequency) {
		SampeFrequency = sampeFrequency;
	}

	public List<MQDeviceRecordData> getData() {
		return data;
	}

	public void setData(List<MQDeviceRecordData> data) {
		this.data = data;
	}

}
