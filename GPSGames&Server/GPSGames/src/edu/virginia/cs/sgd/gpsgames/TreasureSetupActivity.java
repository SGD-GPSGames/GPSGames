package edu.virginia.cs.sgd.gpsgames;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TreasureSetupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_treasure_setup);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_treasure_setup, menu);
		return true;
	}

}
