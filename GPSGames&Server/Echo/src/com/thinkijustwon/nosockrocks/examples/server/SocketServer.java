package com.thinkijustwon.nosockrocks.examples.server;

import java.io.IOException;
import java.net.ServerSocket;

import com.thinkijustwon.nosockrocks.socks.StringSocketHandler;



/**
 * Runs the SocketServer demo on port 7777
 * @author Hunter Williams
 *
 */
public class SocketServer {

	public static int SERVER_PORT = 7777;

	public static void main(String[] args) {
		
		//creates server socket
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
		
        while (true) {
            // listen for incoming clients
            StringSocketHandler sock = new StringSocketHandler(serverSocket.accept());
            System.out.println("new client");
     		
        
        	while(!sock.isClosed()){
        		if (!sock.getIncomingMessages().isEmpty()){
        			
	        		String message = (String) sock.getIncomingMessages().poll();
	        		if (!message.equals("null")){
		    			sock.write("*"+message);
		    			System.out.println("line:"+message);
	        		}
        		
        		}
    
        	}
        	System.out.println("client disconnected");
        	sock.close();
            
        }
        
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    
	}

}
