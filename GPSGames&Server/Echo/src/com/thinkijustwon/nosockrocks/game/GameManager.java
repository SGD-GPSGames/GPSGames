package com.thinkijustwon.nosockrocks.game;

import java.util.ArrayList;

import com.thinkijustwon.nosockrocks.user.UserThread;

public class GameManager {

	private static GameManager instance;
	private int CUR_GAME_ID;
	
	private ArrayList<Game> activeGames;
	private ArrayList<Thread> activeGameThreads;
	private ArrayList<Integer> activeGameIDs;
	
	private GameManager(){
		activeGames = new ArrayList<Game>();
		activeGameIDs = new ArrayList<Integer>();
		activeGameThreads = new ArrayList<Thread>();
	}
	
	public static GameManager getInstance(){
		if (instance == null){
			instance = new GameManager();
		}
		
		return instance;
	}
	
	public ArrayList<Game> getGames(){
		return activeGames;
	}
	
	public synchronized Game createGame(UserThread userThread){
		Game g = new Game(userThread,CUR_GAME_ID);
		activeGames.add(g);
		activeGameIDs.add(CUR_GAME_ID);
		CUR_GAME_ID++;
		Thread t = new Thread(g);
		activeGameThreads.add(t);
		t.start();
		return g;
	}
	
	public synchronized boolean joinGameByID(UserThread userThread, int game_id){
		Game g =this.getGameByID(game_id);
		if (g == null){
			return false;
		}
		return g.Join(userThread);
	}
	
	public Game getGameByID(int game_id){
		for (int i = 0; i < activeGames.size(); i ++){
			if (activeGames.get(i).getID() == game_id){
				return activeGames.get(i);
			}
		}
		return null;
	}
}
