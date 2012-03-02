package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 */
public class NotDirException extends DoozerException{
	private static final long serialVersionUID = -1102521807534245718L;

	public NotDirException(String message, int code) {
		super(message, code);
	}

}
