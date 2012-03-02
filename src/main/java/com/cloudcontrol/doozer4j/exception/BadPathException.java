package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 */
public class BadPathException extends DoozerException{
	private static final long serialVersionUID = -3114692154310381673L;
	
	public BadPathException(String message, int code) {
		super(message, code);
	}
}
