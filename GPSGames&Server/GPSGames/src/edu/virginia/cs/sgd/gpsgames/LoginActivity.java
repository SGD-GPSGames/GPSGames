package edu.virginia.cs.sgd.gpsgames;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.virginia.cs.sgd.gpsgames.connection.StringSocketHandler;


public class LoginActivity extends Activity {

	private final static String TAG = "GPSGamesLOGIN";

	private ServerConnectionService mBoundService;
	private ServiceConnection mConnection;
	private boolean mIsBound = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"started");
		setContentView(R.layout.activity_echo);

		//String ip = "137.54.20.116";
		//int port = 7777;

		final EditText input = (EditText) findViewById(R.id.echoInput);
		final TextView response = (TextView) findViewById(R.id.echoResponse);
		final Button submitButton = (Button) findViewById(R.id.echoSubmit);

		//Log.d(TAG,"attempted to create socket");
		

		//final StringSocketHandler socket = new StringSocketHandler(
		//		new Socket(ip, port));
		//Log.d(TAG,"attempted to connect to socket");
		
		mConnection = new ServiceConnection() {
		    public void onServiceConnected(ComponentName className, IBinder service) {
		        // This is called when the connection with the service has been
		        // established, giving us the service object we can use to
		        // interact with the service.  Because we have bound to a explicit
		        // service that we know is running in our own process, we can
		        // cast its IBinder to a concrete class and directly access it.

				Log.d(TAG,"bound to server service");
		        mBoundService = ((ServerConnectionService.LocalBinder)service).getService();

		        // Tell the user about this for our demo.
		        Toast.makeText(LoginActivity.this, "Connected to GPS Server Connection Service",
		                Toast.LENGTH_SHORT).show();

		    }

		    public void onServiceDisconnected(ComponentName className) {
		        // This is called when the connection with the service has been
		        // unexpectedly disconnected -- that is, its process crashed.
		        // Because it is running in our same process, we should never
		        // see this happen.
		    	Log.d(TAG,"UNbound to server service");
		        mBoundService = null;
		        Toast.makeText(LoginActivity.this, "Disconnected from GPS Server Connection Service",
		                Toast.LENGTH_SHORT).show();
		    }
		};

		doBindService();
//		submitButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				String textString = input.getText().toString();
//				if (socket.write(textString))
//					Log.d(TAG,"message sent");
//				else
//					Log.d(TAG,"message failed");
//			}
//		});

	}




	

	void doBindService() {
	    // Establish a connection with the service.  We use an explicit
	    // class name because we want a specific service implementation that
	    // we know will be running in our own process (and thus won't be
	    // supporting component replacement by other applications).

		Log.d(TAG,"attempting to bind to server service");
		Intent mIntent = new Intent(this, ServerConnectionService.class);
		mIsBound = bindService(mIntent, mConnection, BIND_AUTO_CREATE);
	    Log.d(TAG,"server service connection:"+mIsBound);
	}

	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        unbindService(mConnection);
	        mIsBound = false;
	    }
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    doUnbindService();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
