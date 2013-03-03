package edu.virginia.cs.sgd.gpsgames;

import edu.virginia.cs.sgd.gpsgames.util.Constants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
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
		
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveToCreateActivity();	
			}
		});
		
		
	}

	
	public void setUpUI(){
		welcome = (TextView) findViewById(R.id.menu_welcome);
		create = (Button) findViewById(R.id.menu_create);
		join = (Button) findViewById(R.id.menu_join);
		gameList = (ListView)findViewById(R.id.menu_gameList);	
	}
	
	public void moveToCreateActivity() {
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, MenuActivity.class);
		startActivity(mainActivity);
	}
	
	public void populateGameList() {
		String[]  games = { "Game 1", "Game 2", "Game 3", "Game 4", "Game 5", "Game 6", "Game 7", "Game 8"};
		ArrayAdapter adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, games);
		
		gameList.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}
	
	
	
}
