package edu.virginia.cs.sgd.gpsgames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CreateActivity extends FragmentActivity{

    TextView instructions;
    Button race;
    Button treasure;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
		setUpUI();
		
		instructions.setText("Choose Your Game Type");
			
		race.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveToRaceSetup();	
			}
		});
		
		treasure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveToTreasureSetup();	
			}
		});
		
		
	}

	
	public void setUpUI(){
		instructions = (TextView) findViewById(R.id.create_instructions);
		race = (Button) findViewById(R.id.button_create_race);
		treasure = (Button) findViewById(R.id.button_create_treasure);
	}
	
	public void moveToRaceSetup() {
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, RaceSetupActivity.class);
		startActivity(mainActivity);
	}
	
	public void moveToTreasureSetup() {
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, TreasureSetupActivity.class);
		startActivity(mainActivity);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}
	
	
	
}
