package com.thinkijustwon.nosockrocks.game;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.thinkijustwon.nosockrocks.user.UserThread;

public class Game implements Runnable{

	private ArrayList<UserThread> userThreads;
	private UserThread creator;
	private int capacity = 2; //people in game
	private int gameID;
	private String gameTitle; 
	
	BlockingQueue<GameMessage> incomingMessages;
	
	public Game(UserThread creator, int gameID){
		userThreads = new ArrayList<UserThread>();
		incomingMessages = new LinkedBlockingQueue<GameMessage>();
		this.creator = creator;
		this.Join(this.creator);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			GameMessage g = incomingMessages.poll();
			if (g != null){
				//do shit
				String text = g.getUser().getName()+","+g.getMessage();
				for (int i = 0; i< userThreads.size(); i++){
					if (userThreads.get(i).equals(g.getUser())){
						System.out.println("same:"+text);
						continue;
					}
					else
					{
						System.out.println("to:"+userThreads.get(i).getUser().getName()+text);
						userThreads.get(i).writeMessage(text);
					}
				}
			}
		}
		
	}
	
	protected boolean Join(UserThread user){
		if (userThreads.size() < capacity){
			return userThreads.add(user);
		}
		return false;
	}
	
	public int getID(){
		return gameID;
	}
	
	
	@Override
	public String toString(){
		return "<game id='"+this.gameID+"' game title='"+this.gameTitle+"'/>";		
	}
	
	public synchronized boolean sendGameMessage(GameMessage g){
		return this.incomingMessages.offer(g);
	}
	
}
