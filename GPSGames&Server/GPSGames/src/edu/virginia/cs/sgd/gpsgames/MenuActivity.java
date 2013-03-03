package edu.virginia.cs.sgd.gpsgames;

import edu.virginia.cs.sgd.gpsgames.util.Constants;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends FragmentActivity{

    TextView welcome;
    Button create;
    Button join;
    ListView gameList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		setUpUI();
		
		welcome.setText("Welcome " + getIntent().getStringExtra(Constants.USERNAME) + "!");
		
		populateGameList();
		
		
	}

	
	public void setUpUI(){
		welcome = (TextView) findViewById(R.id.menu_welcome);
		create = (Button) findViewById(R.id.menu_create);
		join = (Button) findViewById(R.id.menu_join);
		gameList = (ListView)findViewById(R.id.menu_gameList);
		
	}
	
	public void populateGameList() {
		//gameList.add
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}
	
	
	
}
