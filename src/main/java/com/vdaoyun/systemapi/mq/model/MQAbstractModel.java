package com.vdaoyun.systemapi.mq.model;

import java.io.Serializable;

abstract class MQAbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 设备编号
	 */
	private String TerminalID;
	
	/**
	 * 推送时间
	 */
	private String PostTime;

	public String getTerminalID() {
		return TerminalID;
	}

	public void setTerminalID(String terminalID) {
		TerminalID = terminalID;
	}

	public String getPostTime() {
		return PostTime;
	}

	public void setPostTime(String postTime) {
		PostTime = postTime;
	}
	
	
}
