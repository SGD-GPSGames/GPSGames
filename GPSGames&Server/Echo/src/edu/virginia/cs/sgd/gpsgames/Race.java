package edu.virginia.cs.sgd.gpsgames;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.thinkijustwon.nosockrocks.game.GameMessage;
import com.thinkijustwon.nosockrocks.user.UserThread;

public class Race extends Game<RacePlayer> {


	private TimeThread tt;
	private int[] startTime;
	private int[] endTime;

	
	private Point start;
	private Point end;
	
	public Race(UserThread creator, int gameID, String gameType, String name, int[] startTime, int[] endTime, LatLng start, LatLng end) {
		super(creator, gameID, gameType, name);

		this.startTime = startTime;
		this.endTime = endTime;
		
		this.start = new Point(start, "Start", BitmapDescriptorFactory.HUE_BLUE);
		this.end = new Point(end, "End", BitmapDescriptorFactory.HUE_BLUE);

		addPoint(this.start);
		addPoint(this.end);
		
		tt = new TimeThread(this);
		tt.start();
	}
	
	@Override
	public void playerCollision(RacePlayer player, RacePlayer player2) {
		
	}

	@Override
	public void onCollision(RacePlayer player, Point point) {
		if(point == start && player.isReady() && !player.isRacing()) {
			player.setRacing(true);
			player.start();
		}
		else if(point == end && player.isRacing()) {
			player.setRacing(false);
			player.stop();
			
			player.message("Your Time: "+(int)player.getTime()+"s.");
		}
	}

	@Override
	public void applyPowerup(RacePlayer player, Powerup p) {
		
	}

	@Override
	public void processMessage(GameMessage g) {
		for(RacePlayer p : players) {
			if(p.getName().equals(g.getUser().getName())) {
				p.setReady(true);
			}
		}
	}
	
	public int timeCompare(int[] time1, int[] time2) {
		
		for(int i = 0; i < time1.length; i++) {
			if(time1[i] > time2[i]) {
				return -1;
			}
			else if (time1[i] < time2[i]) {
				return 1;
			}
		}
		
		return 0;
	}
	
	public void timeCheck(int[] time) {
		
		if(timeCompare(time, startTime) > -1) {
			setOn(true);
		}
		
		if(timeCompare(time, endTime) < 1) {
			setOn(false);
			tt.stopThread();
		}
		
		checkCollision();
	}

	@Override
	public void addPlayer(String name) {
		
		super.addPlayer(new RacePlayer(name, this));
		
	}
	
}
