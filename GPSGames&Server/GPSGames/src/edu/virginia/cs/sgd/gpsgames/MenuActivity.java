package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

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
import edu.virginia.cs.sgd.gpsgames.util.Constants;


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
		GameController.getInstance().setGameMenuActivity(this);

		GameController.getInstance().getGames();
		//populateGameList();
		
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
		Intent mainActivity = new Intent(this, CreateActivity.class);
		startActivity(mainActivity);
	}
	
	public void populateGameList(ArrayList<String> games) {
		
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
