package com.cloudcontrol.doozer4j.support;

import java.io.IOException;

import com.cloudcontrol.doozer4j.IDoozerClient;
import com.cloudcontrol.doozer4j.exception.DoozerException;
import com.cloudcontrol.doozer4j.msg.Msg.Request;
import com.cloudcontrol.doozer4j.msg.Msg.Request.Builder;
import com.cloudcontrol.doozer4j.msg.Msg.Request.Verb;
import com.cloudcontrol.doozer4j.msg.Msg.Response;
import com.google.protobuf.ByteString;

/**
 * Implementation of the defined methods in {@link IDoozerClient}
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)  
 * 
 * @since 0.0.1
 */
public abstract class AbstractDoozerClient extends AbstractSynchroneousDoozerClient implements IDoozerClient{
	
	/**
	 * <pre> doozer [options] set &lt;path&gt; &lt;value&gt; &lt;rev&gt; </pre>
	 * 
	 * {@link IDoozerClient#set(String, String, long)}
	 */
	@Override
	public long set(String path, String value, long rev) throws DoozerException, IOException {		
		Builder builder = Request.newBuilder();
		
		ByteString byteString = ByteString.copyFrom(value.getBytes());
		
		builder = 
				builder.setPath(path)
					.setValue(byteString)
					.setRev(rev)
					.setVerb(Verb.SET)
		;
			
		Response response = buildResponse(send(builder));
		
		return response.getRev();
	}

	/**
	 * <pre> doozer [options] add &lt;path&gt; &lt;value&gt; </pre>
	 * 
	 * {@link IDoozerClient#add(String, String)}
	 */
	@Override
	public long add(String path, String value) throws DoozerException, IOException {		
		Builder builder = Request.newBuilder();
		
		ByteString byteString = ByteString.copyFrom(value.getBytes());
		
		builder = 
				builder.setPath(path)
					.setOffset(0)
					.setTag(0)
					.setValue(byteString)
					.setRev(0L)
					.setVerb(Verb.SET)
		;
		
		Response response = buildResponse(send(builder));
		return response.getRev();
	}	
	
	/**
	 * <pre> doozer [options] get &lt;path&gt; </pre>
	 * 
	 * {@link IDoozerClient#get(String)}
	 */
	@Override
	public Response get(String path) throws DoozerException, IOException {
		Builder builder = Request.newBuilder();
		
		builder = 
				builder.setPath(path)
					.setTag(0)
					.setVerb(Verb.GET)
		;
		
		return buildResponse(send(builder));
	}
	
	/**
	 * <pre> doozer [options] rev &lt;path&gt; </pre>
	 * 
	 * {@link IDoozerClient#rev(String)}
	 * 
	 * <br>
	 * Note: doozerd v0.8 does not respond the expected value, 
	 * so we fall back to {@link AbstractDoozerClient#stat(String)}
	 */
	@Override
	public long rev(String path) throws DoozerException, IOException {
		return stat(path).getRev();
	}
	
	/**
	 * <pre> doozer [options] stat &lt;path&gt; </pre>
	 * 
	 * {@link IDoozerClient#stat(String)}
	 */
	@Override
	public Response stat(String path) throws DoozerException, IOException {
		Builder builder = Request.newBuilder();

		builder = 
				builder.setPath(path)
					.setVerb(Verb.STAT)
		;
		
		Response response = buildResponse(send(builder));		
		return response;
	}
}
