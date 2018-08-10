package com.vdaoyun.systemapi.web.model.echart;

import java.io.Serializable;

public class ColorStop  implements Serializable{
	
	public ColorStop() {
		// TODO Auto-generated constructor stub
	}
	
	public ColorStop(Double offset_, String color_) {
		this.offset = offset_;
		this.color = color_;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double offset = 0.0;
	
	private String color;

	public Double getOffset() {
		return offset;
	}

	public void setOffset(Double offset) {
		this.offset = offset;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}