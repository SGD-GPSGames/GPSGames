package edu.virginia.cs.sgd.gpsgames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RaceActivity extends Activity {

	Button startButton;
	Button mapButton;
	
	private boolean on;
	private long baseTime;
	
	Handler h2 = new Handler();
	Runnable run = new Runnable() {

	        @Override
	        public void run() {
	           updateTime();
	           h2.postDelayed(this, 500);
	        }
	};
	
	public void updateTime() {
		TextView time = (TextView) findViewById(R.id.time);
		
		time.setText(Integer.toString((int)((System.nanoTime()-baseTime)/1E9)));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_race);
		

        setUpUI();
		
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
				start();
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
		GameController.getInstance().put("Back", "Race");
		startActivity(mainActivity);
	}
	
	public void start() {
		if(on) {
			return;
		}
		
		baseTime = System.nanoTime();
        h2.postDelayed(run, 0);
		GameController.getInstance().sendMessage("gmessage:Race:start");
        
		on = true;
	}

	
}
