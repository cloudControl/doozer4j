package com.cloudcontrol.doozer4j.support;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.cloudcontrol.doozer4j.exception.BadPathException;
import com.cloudcontrol.doozer4j.exception.DoozerException;
import com.cloudcontrol.doozer4j.exception.IsDirException;
import com.cloudcontrol.doozer4j.exception.MissingArgumentException;
import com.cloudcontrol.doozer4j.exception.NoEntryException;
import com.cloudcontrol.doozer4j.exception.NotDirException;
import com.cloudcontrol.doozer4j.exception.RangeException;
import com.cloudcontrol.doozer4j.exception.ReadOnlyException;
import com.cloudcontrol.doozer4j.exception.RevMismatchException;
import com.cloudcontrol.doozer4j.exception.TooLateException;
import com.cloudcontrol.doozer4j.exception.UnknownVerbException;
import com.cloudcontrol.doozer4j.msg.Msg.Request;
import com.cloudcontrol.doozer4j.msg.Msg.Request.Builder;
import com.cloudcontrol.doozer4j.msg.Msg.Response;

/**
 * This class provides simple doozer IO operations.
 * 
 * Note: All IO operations will be processed synchroneously. 
 * 		 Asynchroneous operations are not supported yet.
 * 
 * @author Denis Neuling (dn@cloudcontrol.de)
 * 
 * @since 0.0.1
 */
public abstract class AbstractSynchroneousDoozerClient {
	private final Logger log = Logger.getLogger(this.getClass());
	
	private InetAddress inetAddress;
	private int port;
	
	private Socket socket;
	private BufferedOutputStream outputStream;
	private BufferedInputStream inputStream;

	public abstract int getTimeout();
	public abstract int getAttempts();
	
	/**
	 * Returns the port on what has to be read and written
	 * 
	 * @return the port to write and read to
	 */
	public int getPort(){
		return port;
	}
	
	/**
	 * Set the port on what the socket has to connect
	 * 
	 * @param port the port to set
	 */
	public void setPort(int port){
		this.port = port;
	}
	
	/**
	 * Returns the inetAddress on what the socket has to connect.
	 * 
	 * @return inetAddress on what the socket has to connect
	 */
	public InetAddress getInetAddress(){
		return inetAddress;
	}
	
	/**
	 * Set the address to try to connect.
	 * 
	 * @param address the address to connect to
	 */
	public void setInetAddress(InetAddress address){
		this.inetAddress = address;
	}
	
	/**
	 * Returns the socket to handle with.
	 * 
	 * @return socket the socket to work with
	 */
	public Socket getSocket(){
		return socket;
	}
	
	/**
	 * Set the socket to handle with.
	 * 
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	
	/**
	 * Is true if the socket is initialized and connected.
	 * @return connected if the socket is connected
	 */
	public boolean isConnected(){
		boolean connected = (this.socket!=null?this.socket.isConnected():false);
		return connected;
	}
	
	/**
	 * Opens the connection to the channel
	 * and provides input output operations from it
	 * 
	 * @throws IOException
	 */
	private void connect() throws IOException{
		if(!isConnected()){
			
			InetAddress address = getInetAddress();
			int port = getPort();
			SocketAddress socketAddress = new InetSocketAddress(address, port);
			
			for(int i = 0; i < getAttempts() ; i ++){
				try{
					log.debug("Connecting to serversocket, attempt:{"+i+"}");
				    socket = new Socket();
				    socket.setSoTimeout(1000);
				    int timeout = getTimeout();
				    socket.connect(socketAddress, timeout);
				    openInputStream();
					openOutputStream();
				    break;
				}catch(IOException ioe){
					if(i>=getAttempts()-1){
						socket.close();
						socket = null;
						
						throw ioe;
					}else{
						log.warn(ioe.getMessage());
					}
				}
			}
		}
	}
	
	
	/**
	 * Connects if necessary to the serversocket and
	 * opens the outputstream to read the response from.
	 * 
	 * @throws IOException
	 */
	private void openOutputStream() throws IOException{
		connect();
		outputStream = new BufferedOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Connects if necessary to the serversocket and
	 * opens the inputstream to write the request into.
	 * 
	 * @throws IOException
	 */
	private void openInputStream() throws IOException{
		connect();
		inputStream = new BufferedInputStream(socket.getInputStream());
	}
	
	/**
	 * Closes the inputstream.
	 * 
	 * @throws IOException
	 */
	private void closeInputStream() throws IOException{
		inputStream.close();
	}

	/**
	 * Calls the Builder to build the response object.
	 *  
	 * @param builder the builder that holds the response
	 * @return response the response that will be builded
	 */
	protected Response buildResponse(com.cloudcontrol.doozer4j.msg.Msg.Response.Builder builder){
		return builder.build();
	}
	
	/**
	 * Note: that method does not allow to receive responses 
	 * that size is more than 2^31-1 (~2GB) bytes within one connection.
	 * 
	 * @return bytes the byte decoded response
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	protected byte[] receive() throws IOException{
		log.debug("Receiving response from inputstream.");
		
		byte[] lenght = new byte[4];
		int responseSize = inputStream.read(lenght);
		
		byte[] responseBuffer = new byte[parseToInt32(lenght)];
		int received = inputStream.read(responseBuffer);
		
		return responseBuffer;
	}
	
	/**
	 * Note: that method does not allow to send requests
	 * that size is more than 2^31-1 (~2GB) bytes within one connection.
	 * 
	 * @throws IOException
	 */
	protected void write(Request request) throws IOException{
		log.debug("Writing request to outputstream.");
		
		int size = request.getSerializedSize();
		
		openOutputStream();
		outputStream.write(parseFromInt32(size));
		outputStream.write(request.toByteArray());
		outputStream.flush();
	}
	
	/**
	 * This will send the request to the concerning doozerd cluster 
	 * and will receive and encode the response. <br>
	 * For more informations see googles protocolbuffers documentation. 
	 * 
	 * @param builder
	 * @return Builder thr builder that holds the response
	 * @throws DoozerException
	 * @throws IOException
	 */
	protected com.cloudcontrol.doozer4j.msg.Msg.Response.Builder send(Builder builder) throws DoozerException, IOException{
		Request request = builder.build();
		write(request);
		
		try{
			byte[] responseBuffer = receive();
			
			com.cloudcontrol.doozer4j.msg.Msg.Response.Builder responseBuilder = com.cloudcontrol.doozer4j.msg.Msg.Response.newBuilder();
			responseBuilder.mergeFrom(responseBuffer);
			
			wrapError(responseBuilder);
			
			closeInputStream();
			disconnect();
			
			log.debug("Successfully received "+responseBuffer.length+" bytes.");
			
			return responseBuilder;
		}catch(DoozerException dze){
			disconnect();
			throw dze;
		}catch(IOException ioe){
			disconnect();
			throw ioe;
		}
	}
	
	/**
	 * Relieves all network resources.
	 * 
	 * @throws IOException
	 */
	private void disconnect() throws IOException{
		try{
			if(outputStream!=null){
				outputStream.close();
				outputStream = null;
				
				/* (non-Javadoc)
				 * 
				 * outputstream.close() releases all associated ressources
				 * so socket is affected too
				 */
			}
			if(inputStream!=null){
				inputStream.close();
				inputStream = null;
			}
		}catch(IOException ioe){
		}finally{
			if(isConnected()){
				
				/* (non-Javadoc)
				 * 
				 * nevertheless we try to close the socket
				 */
				socket.close();
				socket = null;
			}
		}
	}

    /**
	 * <br>
	 * 
	 * OTHER        = 127; 	<br>
	 * TAG_IN_USE   = 1;	<br>
	 * UNKNOWN_VERB = 2;	<br>
	 * READONLY     = 3;	<br>
	 * TOO_LATE     = 4;	<br>
	 * REV_MISMATCH = 5;	<br>
	 * BAD_PATH     = 6;	<br>
	 * MISSING_ARG  = 7;	<br>
	 * RANGE        = 8;	<br>
	 * NOTDIR       = 20;	<br>
	 * ISDIR        = 21;	<br>
	 * NOENT        = 22;	<br>
	 * 
	 * @param responseBuilder the bytecoded response
	 * @return String the error detail 
	 * @throws DoozerException
	 */
	public String wrapError(com.cloudcontrol.doozer4j.msg.Msg.Response.Builder responseBuilder) throws DoozerException{
		Response response = responseBuilder.build();
		
		String message = response.getErrDetail();
		int code = response.getErrCode().getNumber();
		
		switch(code){
		case 127 :
			
			/* (non-Javadoc)
			 * 
			 * that is mostly not an error
			 */
			break;
		case 1 :
			
			/* (non-Javadoc)
			 * 
			 * that is currently not an error
			 */
			break;
		case 2 :
			throw new UnknownVerbException(message, code);
		case 3 :
			throw new ReadOnlyException(message, code);
		case 4 :
			throw new TooLateException(message, code);
		case 5 :
			throw new RevMismatchException(message, code);
		case 6 :
			throw new BadPathException(message, code);
		case 7 :
			throw new MissingArgumentException(message, code);
		case 8 :
			throw new RangeException(message, code);
		case 20 :
			throw new NotDirException(message, code);
		case 21 :
			throw new IsDirException(message, code);
		case 22 :
			throw new NoEntryException(message, code);
		}
		return message;
	}
	
	/**
	 * Parses 4 fields long byte array to 32bit integer value
	 * <br>
	 * [0000][0000][0000][0001] == 1
	 * 
	 * @param bytes the byte array to decode
	 * @return int the value of the encoded byte array
	 */
	public static int parseToInt32(byte[] bytes) {
        return ( bytes[0] <<  24)
             + ((bytes[1] & 0xFF) << 16)
             + ((bytes[2] & 0xFF) << 8)
             + ( bytes[3] & 0xFF)
        ;
    }

	/**
	 * Parses an integer value to 4 fields long byte array
	 * <br>
	 * 1 == [0000][0000][0000][0001]
	 * 
	 * @param value the integer to decode
	 * @return byte[] the byte value of the encoded integer
	 */
    public static byte[] parseFromInt32(int value) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte)value;
        bytes[2] = (byte)(value >>> 8);
        bytes[1] = (byte)(value >>> 16);
        bytes[0] = (byte)(value >>> 24);
        return bytes;
    }
    
}
