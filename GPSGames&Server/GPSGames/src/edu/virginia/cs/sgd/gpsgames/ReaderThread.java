package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

import android.util.Log;

import edu.virginia.cs.sgd.gpsgames.connection.Connection;

public class ReaderThread implements Runnable {

	Connection conn;
	Class activityClass;
	
	public ReaderThread(Connection connection) {
		conn = connection;
	}
	
	@Override
	public void run() {
		while(true) {
			ArrayList<String> messages = conn.retrieve();
			while(messages.size() > 0) {
				String message = messages.get(0);
				messages.remove(0);
				Log.v("Connection", message);
				if(message.equals("login:success")) {
					GameController.getInstance().loggedIn();
				}
			}
		}

	}

}