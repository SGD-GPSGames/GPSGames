package edu.virginia.cs.sgd.gpsgames;

import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RaceActivity extends Activity implements LocationListener{

	Button startButton;
	Button mapButton;
	
	private boolean on;
	private long baseTime;
	
	public void updateTime() {
		TextView time = (TextView) findViewById(R.id.time);
		
		time.setText(Integer.toString((int)((System.nanoTime()-baseTime)/1E9)));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_race);
		
		on = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.race, menu);
		return true;
	}

	public void setUpUI(){
		startButton = (Button) findViewById(R.id.start);
		
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onStart();
			}

		});
		
		mapButton = (Button) findViewById(R.id.map);
		
		mapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				moveToGameMapActivity();
			}

		});
	}

	public void moveToGameMapActivity() {
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, GameMapActivity.class);
		mainActivity.putExtra("Back", "Race");
		startActivity(mainActivity);
	}
	
	public void onStart() {
		if(on) {
			return;
		}
		
		baseTime = System.nanoTime();
		
		on = true;
	}
	@Override
	public void onLocationChanged(Location loc) {
		GameController.getInstance().sendMessage("loc:" + loc.getLatitude() + "," + loc.getLongitude());
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
}
