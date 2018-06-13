package com.vdaoyun.systemapi.mq.model;

import java.io.Serializable;
import java.util.Date;

abstract class MQAbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String TerminalID;
	
	private Date PostTime;

	public String getTerminalID() {
		return TerminalID;
	}

	public void setTerminalID(String terminalID) {
		TerminalID = terminalID;
	}

	public Date getPostTime() {
		return PostTime;
	}

	public void setPostTime(Date postTime) {
		PostTime = postTime;
	}
	
	
}
