package com.thinkijustwon.nosockrocks.examples.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import com.thinkijustwon.nosockrocks.socks.StringSocketHandler;
import com.thinkijustwon.nosockrocks.user.UserThread;



/**
 * Runs the SocketServer demo on port 7777
 * @author Hunter Williams
 *
 */
public class SocketServer {

	public enum SERVER_STATUS {INIT,RUNNING,EXCEPTION};
	private SERVER_STATUS status = SERVER_STATUS.INIT;
	private int port;
	
	public static ArrayList<UserThread> users;
	public static ArrayList<Thread> threads;
	
	private ServerSocket serverSocket;

	public SocketServer(int port){
		users = new ArrayList<UserThread>();
		threads = new ArrayList<Thread>();
		
		this.port = port;
		status = SERVER_STATUS.INIT;
	}
	
	public void run() {
		
		while (status != SERVER_STATUS.EXCEPTION)
		{
			if (status == SERVER_STATUS.INIT){
				try {
					System.out.println("Starting...");
					serverSocket = new ServerSocket(port);
					System.out.println("Server started.....");
					status = SERVER_STATUS.RUNNING;
				}
				catch (IOException e1) {
					e1.printStackTrace();
					status = SERVER_STATUS.EXCEPTION;
				}
			}
			else if (status == SERVER_STATUS.RUNNING)
			{
				 StringSocketHandler newSock;
				try {
					newSock = new StringSocketHandler(serverSocket.accept());
					 System.out.println("new client");
		        	 UserThread newUser = new UserThread(newSock);
		        	 users.add(newUser);
		             Thread t = new Thread(newUser);
		             threads.add(t);
		             t.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public SERVER_STATUS getServerStatus(){
		return status;
	}

}
