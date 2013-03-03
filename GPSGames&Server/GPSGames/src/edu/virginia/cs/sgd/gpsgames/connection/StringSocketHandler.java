package edu.virginia.cs.sgd.gpsgames.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;

/**
 * StringSocketHandler creates an easy to read from and write to
 * interface to a socket by spawning two threads
 * @author Hunter Williams
 *
 */
public class StringSocketHandler {

	private final Socket socket;
	boolean connected = false;
	final PrintWriter out;
	final BufferedReader in;
	BlockingQueue<String> outgoing = new LinkedBlockingQueue<String>();
	BlockingQueue<String> incoming = new LinkedBlockingQueue<String>();
	private Thread reader,writer;
	
	private static final String TAG = "GPSSocketHandler";
	
	/**
	 * Creates a new SocketHandler
	 * @param s Socket to handle
	 * @throws IOException
	 */
	public StringSocketHandler(final Socket s) throws IOException{
		this.socket = s;
		out = new PrintWriter(socket.getOutputStream(), 
			         true);
		in = new BufferedReader(new InputStreamReader(
                 socket.getInputStream())); 
		Log.d(TAG,"Created reader/writer");
		connected = true;
		reader = new Thread(new SocketReader(this));
		writer = new Thread(new SocketWriter(this));
		reader.start();
		writer.start();
		Log.d(TAG,"Created reader/writer threads");
	}
	
	/**
	 * Returns whether or not the socket is connected
	 * @return
	 */
	public boolean isConnected(){
		return socket.isConnected();
	}
	
	/**
	 * Returns whether or not the socket is closed
	 * @return
	 */
	public boolean isClosed(){
		return socket.isClosed();
	}
	
	/**
	 * Allows the threads to die and closes the socket
	 */
	public void close() throws IOException{
		connected = false;
		socket.close();
	}
	
	/**
	 * Puts a message into the outgoing queue
	 * @param message Message to send
	 * @return Whether or not the message was accepted into the queue
	 */
	public boolean write(String message){
		if (message == null)
			return false;
		return outgoing.offer(message);
	}
	
	/**
	 * Returns the queue of incoming messages
	 * @return
	 */
	public BlockingQueue<String> getIncomingMessages(){
		return incoming;
	}
	
	/**
	 * Returns the queue of outgoing messages
	 * @return
	 */
	public BlockingQueue<String> getOutgoingMessages(){
		return outgoing;
	}
	
	/**
	 * Handles writing to the Socket
	 * Simply writes any messages from the queue to the Socket
	 * @author Hunter
	 *
	 */
	private class SocketWriter implements Runnable{
		private StringSocketHandler handler;
		private SocketWriter(StringSocketHandler handler){
			this.handler = handler;
		}
		@Override
		public void run() {
			while(connected){
				String message = (String) handler.getOutgoingMessages().poll();
				out.println(message);
			}
			out.close();
		}
	}
	
	/**
	 * Handles reading from the Socket
	 * Simply reads any messages from the Socket and puts them 
	 * in the queue
	 * @author Hunter
	 *
	 */
	private class SocketReader implements Runnable{
		private StringSocketHandler handler;
		private SocketReader(StringSocketHandler handler){
			this.handler = handler;
		}
		@Override
		public void run() {
			try{
				while (connected){
						if (in.ready()){
							String line = in.readLine();
							if (line != null && !line.equals("null")){
								handler.getIncomingMessages().offer(line);
							}
						}
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
}