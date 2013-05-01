package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import edu.virginia.cs.sgd.gpsgames.connection.Connection;
import edu.virginia.cs.sgd.gpsgames.util.Util;

public class GameController {
	
	private final static String TAG = "GPSGamesLOGIN";
	
	private static GameController instance;
	
	private LoginActivity login;
	private MenuActivity menu;

	private ServiceConnection mConnection;

	protected ServerConnectionService mBoundService;

	protected ReaderThread thread;

	private boolean mIsBound;
	

	public static GameController getInstance() {
		if(instance == null) {
			instance = new GameController();
		}
		return instance;
	}
	
	
	private GameController() {
		
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
		sendMessage("login:" + user + "," + pass);

	}
	
	public void loggedIn(){
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
		int curp = 0; 
		ArrayList<String>  games = new ArrayList<String>();
		String [] gamelist = rawGamesList.split(";");
		//games.add();
		/*while(curp < rawGamesList.length() ){
			int iindex = rawGamesList.indexOf(",");
			int tindex = rawGamesList.indexOf("title='", curp);
			int end = rawGamesList.indexOf(";", curp);
			String
			String game = rawGamesList.substring(tindex+7, end-1);
			
			curp = end; 
		}*/
		menu.populateGameList(games);
	}
	
	
}
