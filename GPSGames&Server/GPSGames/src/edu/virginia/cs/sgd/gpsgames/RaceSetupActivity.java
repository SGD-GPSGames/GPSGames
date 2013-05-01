package edu.virginia.cs.sgd.gpsgames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

public class RaceSetupActivity extends Activity {

	LatLng start;
	LatLng end;
	
	Button selectStart;
	Button selectEnd;
	Button createGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_race_setup);
		// Show the Up button in the action bar.
		
		setUpUI();
		

		selectEnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pickEnd();
			}
		});
		
		selectStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pickStart();
			}
		});
		
		createGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createGame();
			}
		});
		
		getPoints();
	}

	protected void onResume() {
		super.onResume();
		
		getPoints();
	}
	
	public void setUpUI(){
		selectStart = (Button) findViewById(R.id.pick_start);
		selectEnd = (Button) findViewById(R.id.pick_end);
		createGame = (Button) findViewById(R.id.create_game);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_race_setup, menu);
		return true;
	}
	
	public void moveToSelectActivity(String name, LatLng startingPoint) {
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, SelectPointActivity.class);
		GameController.getInstance().put("Pick", name);
		
		if(startingPoint != null) {
			double[] pointArr = {startingPoint.latitude, startingPoint.longitude};
			GameController.getInstance().put("Point", pointArr);
		}
		startActivity(mainActivity);
	}
	
	public void pickStart() {
		moveToSelectActivity("Start", start);
	}

	public void pickEnd() {
		moveToSelectActivity("End", end);
	}

	private void getPoints() {
		
		Object n = GameController.getInstance().get("Pick");
		
		String name = n == null ? "" : (String) n;
		
		GameController.getInstance().sendMessage("Name: " + name);
		
		if(name.equals("Start")) {
			double[] coords = (double[]) GameController.getInstance().get("Point");
			start = new LatLng(coords[0], coords[1]);
		}
		else if(name.equals("End")) {
			double[] coords = (double[]) GameController.getInstance().get("Point");
			end = new LatLng(coords[0], coords[1]);
		}
		else {
			
		}
		
	}

	public String getName() {
		EditText txt = (EditText) findViewById(R.id.title);
		
		String title = txt.getText().toString();

		return title;
	}
	
	public String getTime(int type) {
		DatePicker d = (DatePicker) findViewById(R.id.startDate);
		
		int y = d.getYear();
		int mo = d.getMonth();
		int day = d.getDayOfMonth();
		
		TimePicker t = (TimePicker) findViewById(type);
		
		int h = t.getCurrentHour();
		int m = t.getCurrentMinute();
		return y+","+mo+","+day+","+h+","+m;
	}
	
	public String getStartTime() {
		return getTime(R.id.startTime);
	}
	public String getEndTime() {
		return getTime(R.id.endTime);
	}
	
	public String createMessage() {
		return "game:create:Race:"+getName()+":"+getStartTime()+";"+getEndTime()+";"+start.latitude+","+start.longitude+";"+end.latitude+","+end.longitude;
	}

	public void moveToRaceActivity() {
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, RaceActivity.class);
		startActivity(mainActivity);
	}
	
	public void createGame() {
		if(start == null || end == null) {
			// TODO Error
			//return;
			start = new LatLng(0,0);
			end = new LatLng(0, 1);
		}
				
		GameController.getInstance().sendMessage(createMessage());
		
		moveToRaceActivity();
	}
	
}
