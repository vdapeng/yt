package com.vdaoyun.systemapi.web.model.echart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WBackgroundColor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type = "linear";
	
	private Integer x = 0;
	
	private Integer y = 0;
	
	private Integer x2 = 0;
	
	private Integer y2 = 1;
	
	private Boolean globalCoord = false;
	
	private List<ColorStop> colorStops = new ArrayList<>();
	
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Integer getX() {
		return x;
	}



	public void setX(Integer x) {
		this.x = x;
	}



	public Integer getY() {
		return y;
	}



	public void setY(Integer y) {
		this.y = y;
	}



	public Integer getX2() {
		return x2;
	}



	public void setX2(Integer x2) {
		this.x2 = x2;
	}



	public Integer getY2() {
		return y2;
	}



	public void setY2(Integer y2) {
		this.y2 = y2;
	}



	public Boolean getGlobalCoord() {
		return globalCoord;
	}



	public void setGlobalCoord(Boolean globalCoord) {
		this.globalCoord = globalCoord;
	}



	public List<ColorStop> getColorStops() {
		return colorStops;
	}



	public void setColorStops(List<ColorStop> colorStops) {
		this.colorStops = colorStops;
	}



	

}
