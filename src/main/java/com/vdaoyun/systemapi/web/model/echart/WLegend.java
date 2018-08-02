package com.vdaoyun.systemapi.web.model.echart;

import com.github.abel533.echarts.Legend;

public class WLegend extends Legend {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer pageIconSize;
	
	private String type;

	public Integer getPageIconSize() {
		return pageIconSize;
	}

	public void setPageIconSize(Integer pageIconSize) {
		this.pageIconSize = pageIconSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
