package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 * @since 0.0.1
 */
public class TooLateException extends DoozerException{
	private static final long serialVersionUID = -2812642141665459563L;

	public TooLateException(String message, int code) {
		super(message, code);
	}

}
