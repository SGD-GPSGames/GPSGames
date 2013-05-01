package com.thinkijustwon.nosockrocks.user;

import java.io.IOException;
import java.util.ArrayList;

import com.thinkijustwon.nosockrocks.Authenticator;
import com.thinkijustwon.nosockrocks.game.Game;
import com.thinkijustwon.nosockrocks.game.GameManager;
import com.thinkijustwon.nosockrocks.game.GameMessage;
import com.thinkijustwon.nosockrocks.socks.StringSocketHandler;

public class UserThread implements Runnable{
	private boolean loggedIn = false;
	private boolean needToDisconnect = false;

	private StringSocketHandler userThread;
	
	private User user;
	
	private ArrayList<Game> activeGames;
	
	public UserThread(StringSocketHandler userThread){
		this.userThread = userThread;
		activeGames = new ArrayList<Game>();
		loggedIn = false;
		needToDisconnect = false;
	}

	@Override
	public void run() {

		while(userThread.isConnected()&&!needToDisconnect){
			if (!userThread.getIncomingMessages().isEmpty()){
				
	    		String message = userThread.getIncomingMessages().poll();
	    		if (!message.equals("null")){
	    			handleMessage(message);
	    		}
			
			}
		}
    	try {
			userThread.close();
			System.out.println("client disconnected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleMessage(String message){
		if (!loggedIn){
			if (message.startsWith("login:")){
				user = Authenticator.Authenticate(message.replaceFirst("login:", ""));
				if (user != null){
					loggedIn = true;
					writeMessage("login:success");
					System.out.println("signed in user");
				}
				else
				{
					writeMessage("login:failure");
				}
			}
		}
		else{
			userThread.write("ack:"+message);
			System.out.println("Message Received: " + message);
			
			if (message.startsWith("game:")){
				String action = message.replace("game:", "");
				if (action.startsWith("create:")){
					//figure out what type
					String create = message.replace("create:", "");
					String[] game = create.split(":");
					
					if(game.length != 3) {
						//TODO Fail
					}
					
					String[] args = game[2].split(";");
					
					Game g = GameManager.getInstance().createGame(this, game[0], game[1], args);
					activeGames.add(g);
				}
				if (action.startsWith("list")){
					writeMessage("games:"
							+GameManager.getInstance().getGames().toString());
				}
				if (action.startsWith("join")){
					int game_id = Integer.parseInt(action.replace("join,", ""));
					
					boolean success = GameManager.getInstance().joinGameByID(this, game_id);
					activeGames.add(GameManager.getInstance().getGameByID(game_id));
					writeMessage("join:success="+success);
				}
			}
			if (message.startsWith("gmessage:")){
				String text = message.replace("gmessage:", "");
				for(int i = 0; i < activeGames.size(); i++){
					activeGames.get(i).sendGameMessage(new GameMessage(this.getUser(),text));
				}
			}
			if (message.startsWith("status:")){
				String text = message.replace("status:","");
				if (text.startsWith("games")){
					writeMessage("status:games,"+activeGames.toString());
				}
			}
		}
	}
	
	public synchronized void ForceDisconnect(){
		needToDisconnect = true;
	}
	
	public User getUser(){
		return user;
	}
	
	public synchronized void writeMessage(String message){
		this.userThread.write(message);
	}
}
