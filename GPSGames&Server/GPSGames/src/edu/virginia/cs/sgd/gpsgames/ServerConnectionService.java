package edu.virginia.cs.sgd.gpsgames;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import edu.virginia.cs.sgd.gpsgames.connection.Connection;
import edu.virginia.cs.sgd.gpsgames.util.Util;

public class ServerConnectionService extends Service {
    private static final String TAG = "GPSGamesServerService";

	private NotificationManager mNM;
    private final IBinder mBinder = new LocalBinder();

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private static final int NOTIFICATION_ID = 235789;
    private static final int PROCESS_ID = 1234; 
    
    private static final String IP_ADDRESS = "192.168.1.36";
	private static final int PORT = 7777;

    private Thread t;
    private Connection connection;
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
    	public ServerConnectionService getService() {
            return ServerConnectionService.this;
        }
    }

    @Override
    public void onCreate() {
    	Log.d(TAG,"GPSServer Service started");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // Display a notification about us starting.  We put an icon in the status bar.
        startForeground(PROCESS_ID,makeNotification());
        

		connection = new Connection(IP_ADDRESS,PORT);
		t = Util.performOnBackgroundThread(connection);
        
        
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        Log.d(TAG,"GPSServer Service started");
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION_ID);
        
        //Close the connection
        //connection.close();
        try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        

        // Tell the user we stopped.
        Toast.makeText(this, "GPSGames Server Connection Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Show a notification while this service is running.
     */
    private Notification makeNotification() {

		Notification notification = new Notification(android.R.drawable.ic_menu_info_details,
				"Started GPSGames Server Connection", System.currentTimeMillis());
		
		Context context = getApplicationContext();
		CharSequence contentTitle = "GPSGames Connected";
		CharSequence contentText = "Server" + " connected";

		// This can be changed if we want to launch an activity when notification clicked
		Intent notificationIntent = new Intent();
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);		
    		

		notification.flags = Notification.FLAG_ONGOING_EVENT;

        return notification;
    }
    
    public void stop(){
    	stopSelf();
    }
    
    public Connection getConnection(){
    	return connection;
    }
}