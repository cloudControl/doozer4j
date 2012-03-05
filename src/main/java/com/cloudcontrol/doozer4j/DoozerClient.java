package com.cloudcontrol.doozer4j;

import java.net.InetAddress;

import com.cloudcontrol.doozer4j.support.AbstractDoozerClient;

/**
 * The main class to get access to the doozer cluster
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 * 
 * @since 0.0.1
 */
public class DoozerClient extends AbstractDoozerClient implements IDoozerClient{
	
	private final int DEFAULTTIMEOUT = 1000; 
	private final int DEFAULTATTEMPTS = 3;
	
	/* (non-Javadoc)
	 * 
	 * by default 1 second
	 */
	private int timeout = DEFAULTTIMEOUT;
	
	/* (non-Javadoc)
	 * 
	 * by default 3 attempts
	 */
	private int attempts = DEFAULTATTEMPTS;
	
	public DoozerClient(InetAddress doozerd, int port){
		this.setInetAddress(doozerd);
		this.setPort(port);
	}

	/**
	 * Get the timeout of the connection in time millis. Default is one second.
	 */
	@Override
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Set the timeout of the connection in time millis. Default is one second.
	 */
	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * Returns the attempts to try to connect to the serversocket. Default is three.
	 */
	@Override
	public int getAttempts() {
		return attempts;
	}

	/**
	 * Set the attempts to try to connect to the serversocket. Default is three.
	 */
	@Override
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
}
