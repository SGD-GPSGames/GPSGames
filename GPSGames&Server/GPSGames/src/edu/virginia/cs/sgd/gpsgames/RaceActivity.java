package edu.virginia.cs.sgd.gpsgames;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RaceActivity extends Activity {

	Button startButton;
	Button mapButton;
	Button returnButton;
	
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
		this.setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
		
		returnButton = (Button) findViewById(R.id.returnbutton);
		
		returnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				moveToMenuActivity();
			}

		});
		
		
	}

	public void moveToMenuActivity(){
		Intent menuActivity = new Intent(this, MenuActivity.class);
		startActivity(menuActivity);
	}

	public void moveToGameMapActivity() {
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
	
}
