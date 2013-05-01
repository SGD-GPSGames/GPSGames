package edu.virginia.cs.sgd.gpsgames;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.virginia.cs.sgd.gpsgames.connection.Connection;
import edu.virginia.cs.sgd.gpsgames.util.Constants;
import edu.virginia.cs.sgd.gpsgames.util.Util;


public class LoginActivity extends FragmentActivity {

	private final static String TAG = "GPSGamesLOGIN";

	
	private ReaderThread thread;
	private ServerConnectionService mBoundService;
	private ServiceConnection mConnection;
	private boolean mIsBound = false;
	
	private EditText usernameText;
	private EditText passwordText;
	private Button submitButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"started");
		setContentView(R.layout.activity_login);
		

		GameController.getInstance().setLoginActivity(this);
		//Set up the UI
	
		usernameText = (EditText)findViewById(R.id.loginUsername);
		passwordText = (EditText)findViewById(R.id.loginPassword);
		submitButton = (Button)findViewById(R.id.loginSubmit);
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				GameController.getInstance().sendLogin(usernameText.getText().toString(), passwordText.getText().toString());
			}
		});
		
		
	}
	
	public void moveToMenuActivity() {
		Intent mainActivity = new Intent(this, MenuActivity.class);
		String username = usernameText.getText().toString();
		mainActivity.putExtra(Constants.USERNAME, username);
		startActivity(mainActivity);
	}


	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    GameController.getInstance().doUnbindService();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
