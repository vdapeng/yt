package com.vdaoyun.systemapi.web.model.echart;

import com.github.abel533.echarts.series.Line;

public class WLine extends Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean connectNulls;

	public Boolean getConnectNulls() {
		return connectNulls;
	}

	public void setConnectNulls(Boolean connectNulls) {
		this.connectNulls = connectNulls;
	}
	

}
