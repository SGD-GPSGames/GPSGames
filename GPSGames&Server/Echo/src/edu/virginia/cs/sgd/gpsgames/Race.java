package edu.virginia.cs.sgd.gpsgames;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class Race extends Game<RacePlayer> {

	private Point start;
	private Point end;
	
	public Race(double tolerance, LatLng start, LatLng end) {
		super(tolerance);

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
}
