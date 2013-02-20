package com.thinkijustwon.nosockrocks.examples.terminal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.thinkijustwon.nosockrocks.socks.StringSocketHandler;


/**
 * A telnet like communicator that will
 * read from and print to the console
 * @author Hunter Williams
 *
 */
public class SockTerminal {

	private String ip;
	private int port;
	private boolean connected = false;
	private StringSocketHandler sock;
	private Scanner input;
	
	public SockTerminal (String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public boolean isConnected(){
		return connected;
	}
	
	public void connect(){
		InetAddress serverAddr;
		try {
			serverAddr = InetAddress.getByName(ip);
		
			System.out.println("Will attempt to connect to " + serverAddr + ":" + port);
			 
			this.sock = new StringSocketHandler(new Socket(serverAddr,port));
			this.input = new Scanner(System.in);
			this.connected = true;
			 
			System.out.println("Connected");
		 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private class SSInput implements Runnable{

		@Override
		public void run() {
			while (connected) {
				 
           		String msg = "";
           		if (input.hasNext()){
               		msg = input.nextLine();
                    sock.write(msg);
           		}
				
				
                if (msg.equalsIgnoreCase("exit")){
                	connected = false;
                }
			}
		}
	}
	
	private class SSReader implements Runnable{

		@Override
		public void run() {
			while (connected){
				String reply;
				try {
					reply = sock.getIncomingMessages().take();
					if (reply != null && !reply.equals("null")){
						System.out.println("reply:"+reply);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void close(){
		try {	    
			 
           sock.close();
           System.out.println("Goodbye!");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
		
	public void run(){	
		Thread input = new Thread(new SSInput());
		Thread reader = new Thread(new SSReader());
		input.start();
		reader.start();
	
	}
	
}