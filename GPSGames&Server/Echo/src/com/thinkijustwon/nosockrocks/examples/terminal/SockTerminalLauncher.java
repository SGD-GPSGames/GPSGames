package com.thinkijustwon.nosockrocks.examples.terminal;

import java.util.Scanner;

/**
 * Runs the SockTerminal Demo
 * Simply parrots back input with "*" prepended
 * @author Hunter Williams
 *
 */
public class SockTerminalLauncher {

	/**
	 * Runs the terminal
	 * @param args String ip, int port
	 */
	public static void main(String[] args) {
		boolean setup = false;
		String ip = "";
		int port = -1;
		if (args.length == 2){
			ip = args[1];
			try{
				port = Integer.parseInt(args[2]);
				setup = true;
			}
			catch (NumberFormatException e)
			{
				System.err.print("Port must be an integer");
			}
		}
		
		if (!setup){
			Scanner in = new Scanner(System.in);
			System.out.print("ip: ");
			ip = in.nextLine();
			System.out.print("port: ");
			while (in.hasNextLine())
			{
				if (in.hasNextInt()){
					port = in.nextInt();
					break;
				}
			}
		}
		
		SockTerminal terminal = new SockTerminal(ip,port);
		terminal.connect();
		if (terminal.isConnected()){
			terminal.run();
			while(terminal.isConnected()){
				//wait
			}
			terminal.close();
		}
	}

}
