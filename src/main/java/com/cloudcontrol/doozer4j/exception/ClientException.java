package com.cloudcontrol.doozer4j.exception;

import java.io.IOException;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 * @since 0.0.1
 */
public class ClientException extends IOException{
	private static final long serialVersionUID = 4162291951123779243L;

	public ClientException(){
		
	}
	
	public ClientException(String message){
		super(message);
	}

}
