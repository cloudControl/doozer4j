package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 * 
 * @since 0.0.1
 */
public class NoEntryException extends DoozerException{
	private static final long serialVersionUID = 8821054641261859476L;

	public NoEntryException(String message, int code) {
		super(message, code);
	}

}
