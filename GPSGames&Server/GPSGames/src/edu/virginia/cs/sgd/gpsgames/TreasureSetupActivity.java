package edu.virginia.cs.sgd.gpsgames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TreasureSetupActivity extends Activity {

	double[][] area;
	
	Button selectArea;
	Button createGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_treasure_setup);

		setUpUI();
		
		selectArea.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pickArea();
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

	public void setUpUI(){
		selectArea = (Button) findViewById(R.id.button1);
		createGame = (Button) findViewById(R.id.button3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_treasure_setup, menu);
		return true;
	}
	
	public void pickArea() {
		Intent mainActivity = new Intent(this, SelectAreaActivity.class);
		mainActivity.putExtra("edu.virginia.cs.sgd.gpsgames.Name", "Treasure");
		
		if(area != null) {
			mainActivity.putExtra("edu.virginia.cs.sgd.gpsgames.Points", area);
		}
		startActivity(mainActivity);
	}
	
	public void createGame() {
		if(area != null) {
			if(area.length > 2) {
				
			}
		}
	}
	
	public void getPoints() {
		Bundle ex = getIntent().getExtras();
		
		String name = (ex == null ? null : ex.getString("edu.virginia.cs.sgd.gpsgames.Name"));
		
		if(name == null) {
			return;
		}
		else  {
			Object obj = ex.get("edu.virginia.cs.sgd.gpsgames.Points");

			double[][] pointArr = (obj == null ? null : (double[][]) obj);

			if (pointArr == null) {
				return;
			}
			
			area = pointArr;
		}
		
	}
}
