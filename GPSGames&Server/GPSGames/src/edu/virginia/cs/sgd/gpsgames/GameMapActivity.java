package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

import android.content.Intent;
import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

public class GameMapActivity extends MapActivity {

	String back;
	Button backButton;
	
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
		

		back = getIntent().getExtras().getString("Back");
		backButton = (Button) findViewById(R.id.back);

		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveToLastActivity();
			}
		});
		
	}

	public void moveToLastActivity() {
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Class<?> next = null;
		if(back.equals("Race")) {
			next = RaceActivity.class;
		}
		else if(back.equals("Treasure")) {
			
		}
		
		Intent mainActivity = new Intent(this, next);

		startActivity(mainActivity);
	}
	
}
