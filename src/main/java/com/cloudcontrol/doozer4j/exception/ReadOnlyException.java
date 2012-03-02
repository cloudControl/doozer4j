package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 */
public class ReadOnlyException extends DoozerException{
	private static final long serialVersionUID = 7520638047140745716L;

	public ReadOnlyException(String message, int code) {
		super(message, code);
	}

}
