package com.cloudcontrol.doozer4j.exception;

public class TagInUseException extends DoozerException{
	private static final long serialVersionUID = 4783753351510016522L;

	public TagInUseException(String message, int code) {
		super(message, code);
	}

}