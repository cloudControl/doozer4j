package com.cloudcontrol.doozer4j.exception;

public class RevMismatchException extends DoozerException{
	private static final long serialVersionUID = -3433262254746595974L;

	public RevMismatchException(String message, int code) {
		super(message, code);
	}

}