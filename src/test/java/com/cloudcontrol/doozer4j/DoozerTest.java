package com.cloudcontrol.doozer4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


/**
 * Ignore the test 'cause if the doozerd cannot be called each build will fail...
 * ... and that is suboptimal.
 * 
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 */
//@Ignore
public class DoozerTest {

	private String host = "127.0.0.1";
	private int port = 8046;
	
	private InetAddress address;
	
	@Before
	public void setUp(){
		try {
			address = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAdd(){	
		
		IDoozerClient dz = new DoozerClient(address, port);
		
		try {
			dz.add("/etc/nginx/conf/upstreams.conf", "success");
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testSet(){
		
		IDoozerClient dz = new DoozerClient(address, port);
		
		try {
			dz.set("/set", "success",0L);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testRev(){
		
		IDoozerClient dz = new DoozerClient(address, port);
		
		try {
			long rev = dz.add("/testRev", "success");
				 rev = dz.rev("/testRev");
			Assert.assertNotSame(0, rev);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
