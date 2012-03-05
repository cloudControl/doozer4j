package com.cloudcontrol.doozer4j.support;

import java.io.IOException;

import com.cloudcontrol.doozer4j.IDoozerClient;
import com.cloudcontrol.doozer4j.msg.Msg.Request;
import com.cloudcontrol.doozer4j.msg.Msg.Request.Builder;
import com.cloudcontrol.doozer4j.msg.Msg.Request.Verb;
import com.cloudcontrol.doozer4j.msg.Msg.Response;
import com.google.protobuf.ByteString;

/**
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 * 
 */
public abstract class AbstractDoozerClient extends AbstractSynchroneousDoozerClient implements IDoozerClient{
	
	@Override
	public long set(String path, String value, long rev) throws IOException {		
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

	@Override
	public long add(String path, String value) throws IOException {		
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
	
	@Override
	public Response get(String path) throws IOException {
		Builder builder = Request.newBuilder();
		
		builder = 
				builder.setPath(path)
					.setTag(0)
					.setVerb(Verb.GET)
		;
		
		return buildResponse(send(builder));
	}
	
	/* (non-Javadoc)
	 * 
	 * "doozer [options] rev /path" is faulty
	 * so we fall back to stat
	 */
	@Override
	public long rev(String path) throws IOException{
		return stat(path).getRev();
	}
	
	@Override
	public Response stat(String path) throws IOException{
		Builder builder = Request.newBuilder();

		builder = 
				builder.setPath(path)
					.setVerb(Verb.STAT)
		;
		
		Response response = buildResponse(send(builder));		
		return response;
	}
}
