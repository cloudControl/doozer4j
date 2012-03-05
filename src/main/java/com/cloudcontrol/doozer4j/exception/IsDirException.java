package com.cloudcontrol.doozer4j.exception;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 * @since 0.0.1
 */
public class IsDirException extends DoozerException{
	private static final long serialVersionUID = 5685034582509066083L;

	public IsDirException(String message, int code) {
		super(message, code);
	}

}
