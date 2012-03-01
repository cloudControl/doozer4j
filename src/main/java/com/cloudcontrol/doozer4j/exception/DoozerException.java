package com.cloudcontrol.doozer4j.exception;

import java.io.IOException;

public class DoozerException extends IOException{
	private static final long serialVersionUID = 2935978831408143740L;
	
	private int code;
	
	public DoozerException(String message, int code){
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
