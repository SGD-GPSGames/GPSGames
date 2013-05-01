package com.thinkijustwon.nosockrocks.game;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.thinkijustwon.nosockrocks.user.User;
import com.thinkijustwon.nosockrocks.user.UserThread;

public abstract class Game implements Runnable{

	public ArrayList<UserThread> userThreads;
	private UserThread creator;
	public int capacity = 2;
	private int gameID;
	
	BlockingQueue<GameMessage> incomingMessages;
	
	public Game(UserThread creator, int gameID){
		userThreads = new ArrayList<UserThread>();
		incomingMessages = new LinkedBlockingQueue<GameMessage>();
		this.creator = creator;
		this.Join(this.creator);
	}

	public boolean addUser(UserThread user) {
		addPlayer(user.getUser().getName());
		return userThreads.add(user);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			GameMessage g = incomingMessages.poll();
			if (g != null){
				processMessage(g);
			}
		}
		
	}
	
	public abstract void processMessage(GameMessage g);
	
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
		return "<game id='"+this.gameID+"'/>";		
	}
	
	public synchronized boolean sendGameMessage(GameMessage g){
		return this.incomingMessages.offer(g);
	}
	
	public void sendToAll(String message){
		for (int i = 0; i< userThreads.size(); i++){
			System.out.println("to:"+userThreads.get(i).getUser().getName()+message);
			userThreads.get(i).writeMessage(message);
		}
	}
	
	public void sendToAllButHost(String message){
		User host = this.getHost();
		for (int i = 0; i< userThreads.size(); i++){
			if (userThreads.get(i).equals(host)){
				System.out.println("same:"+message);
				continue;
			}
			else
			{
				System.out.println("to:"+userThreads.get(i).getUser().getName()+message);
				userThreads.get(i).writeMessage(message);
			}
		}
	}
	
	public User getHost(){
		return creator.getUser();
	}
	

	public abstract void addPlayer(String name);
}
