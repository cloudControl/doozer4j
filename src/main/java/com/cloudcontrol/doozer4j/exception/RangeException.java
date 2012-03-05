package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 * @since 0.0.1
 */
public class RangeException extends DoozerException{
	private static final long serialVersionUID = 9031736862595755332L;

	public RangeException(String message, int code) {
		super(message, code);
	}

}
