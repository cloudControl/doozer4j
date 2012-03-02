package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 */
public class UnknownVerbException extends DoozerException{
	private static final long serialVersionUID = 7494352811594535618L;

	public UnknownVerbException(String message, int code) {
		super(message, code);
	}
}
