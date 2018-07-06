package com.vdaoyun.systemapi.exception;

public class ParamException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final String[] params;
	
	public ParamException() {
		super();
		this.params = null;
	}
	
	public ParamException(String message) {
		super(message);
		this.params = null;
	}
	
	public ParamException(String message, String... pams) {
		super(message);
		this.params = pams;
	}

	public String[] getParams() {
		return params;
	}

	
}
