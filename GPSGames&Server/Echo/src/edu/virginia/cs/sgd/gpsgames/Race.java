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
	}
	
	@Override
	public void playerCollision(RacePlayer player, RacePlayer player2) {
		
	}

	@Override
	public void onCollision(RacePlayer player, Point point) {
		if(point == start) {
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
		
	}
	
	public boolean pairCompare() {
		return false;
	}
	
	public void timeCheck(int[] time) {
		
	}
	
}
