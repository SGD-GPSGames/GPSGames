package edu.virginia.cs.sgd.gpsgames;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.example.gpsgames.R;

import edu.virginia.cs.sgd.gpsgames.socks.StringSocketHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_echo);

			String ip = "137.54.21.105";
			int port = 7777;

			final EditText input = (EditText) findViewById(R.id.echoInput);
			final TextView response = (TextView) findViewById(R.id.echoResponse);
			final Button submitButton = (Button) findViewById(R.id.echoSubmit);

			final StringSocketHandler socket = new StringSocketHandler(
					new Socket(ip, port));

			submitButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String textString = input.getText().toString();
					socket.write(textString);
				}
			});
			
			
			final class SSReader implements Runnable{

				@Override
				public void run() {
					while (socket.isConnected()){
						String reply;
						try {
							reply = socket.getIncomingMessages().take();
							if (reply != null && !reply.equals("null")){
								response.setText(reply);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
