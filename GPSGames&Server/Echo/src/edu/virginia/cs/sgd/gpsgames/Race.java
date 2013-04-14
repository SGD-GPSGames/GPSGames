package edu.virginia.cs.sgd.gpsgames;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class Race extends Game {

	private Point start;
	private Point end;
	public Race(double tolerance, LatLng start, LatLng end) {
		super(tolerance);

		this.start = new Point(start, "Start", BitmapDescriptorFactory.HUE_BLUE);
		this.end = new Point(end, "End", BitmapDescriptorFactory.HUE_BLUE);

		putPoint(this.start);
		putPoint(this.end);
	}

	@Override
	public void onCollision(Point p1, Point p2) {
		if(p1 == start || p2 == start) {
			// TODO At start
		}
		else if(p1 == end || p2 == end) {
			// TODO At end
		}
	}

	@Override
	public void applyPowerup(String player, Powerup p) {
		// TODO Auto-generated method stub

	}

}
