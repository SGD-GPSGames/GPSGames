package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class GameMapActivity extends MapActivity {

	public LatLng getCurrentLocation() {
		Location l = map.getMyLocation();
		
		return new LatLng(l.getLatitude(), l.getLongitude());
	}

	public void update(ArrayList<Point> points) {
		
		map.clear();
		
		for(Point p : points) {
			addMarker(p.getPosition(), p.getTitle(), p.getColor());
		}
		
	}


	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.activity_map;
	}
	
	@Override
	public void onClick(LatLng point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUpUI() {
		// TODO Auto-generated method stub
		//show title 
		//show map
		//start button -> timer
		
		
	}
	
	
}
