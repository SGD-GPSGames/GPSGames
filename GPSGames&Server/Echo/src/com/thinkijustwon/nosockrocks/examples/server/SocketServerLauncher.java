package com.thinkijustwon.nosockrocks.examples.server;

public class SocketServerLauncher {


	public static int SERVER_PORT = 7777;
	
	public static void main(String[] args) {

		SocketServer server = new SocketServer(SERVER_PORT);
		
		System.out.println("Launching server");
		while (server.getServerStatus() != SocketServer.SERVER_STATUS.EXCEPTION)
		{
			server.run();
		}
		
		System.out.println("Server terminated");
	}

}
