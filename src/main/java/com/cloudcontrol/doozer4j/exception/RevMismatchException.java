package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 * @since 0.0.1
 */
public class RevMismatchException extends DoozerException{
	private static final long serialVersionUID = -3433262254746595974L;

	public RevMismatchException(String message, int code) {
		super(message, code);
	}

}
