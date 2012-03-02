package com.cloudcontrol.doozer4j;

import java.net.InetAddress;

import com.cloudcontrol.doozer4j.support.AbstractDoozerClient;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
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

	@Override
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	@Override
	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
}
