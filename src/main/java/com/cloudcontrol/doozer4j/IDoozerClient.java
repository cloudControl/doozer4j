package com.cloudcontrol.doozer4j;

import java.io.IOException;

import com.cloudcontrol.doozer4j.exception.DoozerException;
import com.cloudcontrol.doozer4j.msg.Msg.Response;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 *
 */
public interface IDoozerClient{

	/**
	 * Manipulates the timeout
	 * default is 2 seconds
	 * @param timeout
	 */
	public void setTimeout(int timeout);
	public int getTimeout();
	
	/**
	 * Manipulates the attempts to connect to the host
	 * default is 3 seconds
	 * @param attempts
	 */
	public void setAttempts(int attempts);
	public int getAttempts();
	
	/**
	 * 
	 * doozer [options] set <path>
	 * 
	 * @param path the path of the file
	 * @param value the file to set onto the path
	 * @param rev
	 * @return long
	 * @throws IOException
	 */
	public long set(String path, String value, long rev) throws DoozerException, IOException;
	
	/**
	 * 
	 * doozer [options] add <path>
	 * 
	 * @param path the path of the file
	 * @param value the file to set onto the path
	 * @return long
	 * @throws IOException
	 */
	public long add(String path, String value) throws DoozerException, IOException;
	
	/**
	 * 
	 * doozer [options] rev <path>
	 * 
	 * @param path the path of the file
	 * @return long
	 * @throws IOException
	 */
	public long rev(String path) throws DoozerException, IOException;
	
	/**
	 * 
	 * doozer [options] get <path>
	 * 
	 * @param path the path of the file
	 * @return Response
	 * @throws IOException
	 */
	public Response get(String path) throws DoozerException, IOException;
	
	/**
	 * 
	 * @param path the path of the file
	 * @return Response
	 * @throws IOException
	 */
	public Response stat(String path) throws DoozerException, IOException;
}
