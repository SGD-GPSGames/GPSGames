package com.thinkijustwon.nosockrocks.game;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.thinkijustwon.nosockrocks.user.UserThread;

import edu.virginia.cs.sgd.gpsgames.Race;

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
	
	public synchronized Game createGame(UserThread userThread, String gameType, String name, String[] args) {
		
		Game g = null;
		
		if(gameType.equals("Race")) {

			String[] startTimeStr = args[0].split(",");
			int[] startTime = new int[startTimeStr.length];
			
			String[] endTimeStr = args[1].split(",");
			int[] endTime = new int[endTimeStr.length];

			for(int i = 0; i < startTime.length; i++) {
				startTime[i] = Integer.parseInt(startTimeStr[i]);
				endTime[i] = Integer.parseInt(endTimeStr[i]);
			}
			
			String[] startStr = args[2].split(",");
			LatLng start = new LatLng(Double.parseDouble(startStr[0]), Double.parseDouble(startStr[1]));
			String[] endStr = args[3].split(",");
			LatLng end = new LatLng(Double.parseDouble(endStr[0]), Double.parseDouble(endStr[1]));
			
			g = new Race(userThread,CUR_GAME_ID, gameType, name, startTime, endTime, start, end);
		}
		else if(gameType.equals("Treasure")) {
			
		}
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
