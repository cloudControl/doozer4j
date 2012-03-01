package com.cloudcontrol.doozer4j.exception;

public class MissingArgumentException extends DoozerException{
	private static final long serialVersionUID = -9139913075025572051L;

	public MissingArgumentException(String message, int code) {
		super(message, code);
	}

}
