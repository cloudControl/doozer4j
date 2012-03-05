package com.cloudcontrol.doozer4j;

import java.io.IOException;

import com.cloudcontrol.doozer4j.exception.DoozerException;
import com.cloudcontrol.doozer4j.msg.Msg.Response;

/**
 * The main interface for the doozerClient
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 * 
 * @since 0.0.1
 */
public interface IDoozerClient{

	/**
	 * Manipulates the timeout. Default: 2 seconds.
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout);
	public int getTimeout();
	
	/**
	 * Manipulates the attempts to connect to the host default is 3 attempts
	 * 
	 * @param attempts
	 */
	public void setAttempts(int attempts);
	public int getAttempts();
	
	/** 
	 * <pre> doozer [options] set &lt;path&gt; &lt;rev&gt; </pre>
	 * 
	 * @param path the path of the file
	 * @param value the file to set onto the path
	 * @param rev
	 * @return long
	 * @throws IOException
	 */
	public long set(String path, String value, long rev) throws DoozerException, IOException;
	
	/**
	 * <pre> doozer [options] add &lt;path&gt; </pre>
	 * 
	 * @param path the path of the file
	 * @param value the file to set onto the path
	 * @return long
	 * @throws IOException
	 */
	public long add(String path, String value) throws DoozerException, IOException;
	
	/**
	 * <pre> doozer [options] rev &lt;path&gt; </pre>
	 * 
	 * @param path the path of the file
	 * @return long
	 * @throws IOException
	 */
	public long rev(String path) throws DoozerException, IOException;
	
	/**
	 * <pre> doozer [options] get &lt;path&gt; </pre>
	 * 
	 * @param path the path of the file
	 * @return Response
	 * @throws IOException
	 */
	public Response get(String path) throws DoozerException, IOException;
	
	/**
	 * <pre> doozer [options] stat &lt;path&gt; </pre>
	 * 
	 * @param path the path of the file
	 * @return Response
	 * @throws IOException
	 */
	public Response stat(String path) throws DoozerException, IOException;
}
