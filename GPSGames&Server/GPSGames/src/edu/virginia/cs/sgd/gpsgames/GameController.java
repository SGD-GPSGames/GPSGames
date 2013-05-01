package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import edu.virginia.cs.sgd.gpsgames.connection.Connection;
import edu.virginia.cs.sgd.gpsgames.util.Util;

public class GameController {
	
	private final static String TAG = "GPSGamesLOGIN";
	
	private static GameController instance;
	
	private LoginActivity login;
	private MenuActivity menu;
	private RaceActivity race;
	
	private ServiceConnection mConnection;

	protected ServerConnectionService mBoundService;

	protected ReaderThread thread;
	
	private boolean mIsBound;

	private Handler ui = new Handler();
	private Map<String, Object> map = new HashMap<String, Object>();
	
	private boolean loggedIn = false;

	public static GameController getInstance() {
		if(instance == null) {
			instance = new GameController();
		}
		return instance;
	}
	
	private GameController() {
		loggedIn = false;
	}
	
	public void setLoginActivity(final LoginActivity login) {
		this.login = login;
		
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
		        Toast.makeText(login, "Connected to GPS Server Connection Service",
		                Toast.LENGTH_SHORT).show();
		        
				thread = new ReaderThread(mBoundService.getConnection());
				Util.performOnBackgroundThread(thread);

		    }

		    public void onServiceDisconnected(ComponentName className) {
		        // This is called when the connection with the service has been
		        // unexpectedly disconnected -- that is, its process crashed.
		        // Because it is running in our same process, we should never
		        // see this happen.
		    	Log.d(TAG,"UNbound to server service");
		        mBoundService = null;
		        Toast.makeText(login, "Disconnected from GPS Server Connection Service",
		                Toast.LENGTH_SHORT).show();
		    }
		};

		doBindService();
		
	}
	
	public void setRaceActivity(RaceActivity raceA) {
		race = raceA;
	}
	
	void doBindService() {
	    // Establish a connection with the service.  We use an explicit
	    // class name because we want a specific service implementation that
	    // we know will be running in our own process (and thus won't be
	    // supporting component replacement by other applications).

		Log.d(TAG,"attempting to bind to server service");
		Intent mIntent = new Intent(login, ServerConnectionService.class);
		mIsBound = login.bindService(mIntent, mConnection, Activity.BIND_AUTO_CREATE);
	    Log.d(TAG,"server service connection:"+mIsBound);
	}
	
	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        login.unbindService(mConnection);
	        mIsBound = false;
	    }
	}
	
	public void sendLogin(String user, String pass){
		if (!loggedIn){
			sendMessage("login:" + user + "," + pass);
		}
		else
		{
			Toast.makeText(login, "Already logged in", Toast.LENGTH_SHORT);
			loggedIn();
		}
	}
	
	public void loggedIn(){
		loggedIn = true;
		login.moveToMenuActivity();
	}


	public void getGames() {
		sendMessage("status:games");	
	}
	
	public void sendMessage(String message){
		Connection connection = mBoundService.getConnection();
		connection.send(message);
	}


	public void setGameMenuActivity(MenuActivity menuActivity) {
		menu = menuActivity; 
	}
	
	public void populateGames(String rawGamesList){
		//process the string
		//place holder

		final String [] gamelist = rawGamesList.split(";");

		
		ui.post(new Runnable() {
			public void run() {

				menu.populateGameList(gamelist);
				
			}
		});
	}
	
	public void put(String key, Object value) {
		if(map.containsKey(key)) {
			map.remove(key);
		}
		map.put(key, value);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public LatLng getCurrentLocation() {
		
		LocationManager service = (LocationManager) login.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = service.getBestProvider(criteria, false);
		Location location = service.getLastKnownLocation(provider);
		LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());

		return userLocation;
	}
	
	public void raceMessage(String msg) {
		race.processMessage(msg);
	}
}
