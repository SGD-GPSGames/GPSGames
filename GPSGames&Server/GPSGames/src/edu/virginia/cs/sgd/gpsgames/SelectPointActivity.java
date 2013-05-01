package edu.virginia.cs.sgd.gpsgames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class SelectPointActivity extends MapActivity implements PointRequestor {

	private String name;
	private LatLng point;
	private Button select;
	
	@Override
	public int getLayout() {
		return R.layout.activity_select;
	}
	
	@Override
	public void setUpUI() {
		select = (Button) findViewById(R.id.point_select_button);
		
		select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				moveToRaceSetupActivity();
			}
			
		});

		//name = (String) GameController.getInstance().get("Pick");
		
		Object pointObj = GameController.getInstance().get("Point");
		if(pointObj != null) {
			double[] pointArr = (double[]) pointObj;
			LatLng point = new LatLng(pointArr[0], pointArr[1]);
			this.point = point;
			addMarker(point, "Current Point", BitmapDescriptorFactory.HUE_RED);
		}
	}

	@Override
	public void onClick(LatLng point) {
		
//		PointPicker picker = new PointPicker(this);
//		
//		picker.pickPoint(point);
		
		pointCheck(point, true);
	}
	
	public void moveToRaceSetupActivity() {
		
		//REPLACE MenuActivity.clss BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, RaceSetupActivity.class);
		
		//GameController.getInstance().put("Pick", name);
		
		if(point != null) {
			double[] pointArr = {point.latitude, point.longitude};
			GameController.getInstance().put("Point", pointArr);
		}
		
		map.clear();
		point = null;
		
		startActivity(mainActivity);
	}

	@Override
	public void pointCheck(LatLng point, boolean valid) {
		
		TextView text = (TextView) findViewById(R.id.info_text);
		if(valid) {
			map.clear();
			
			this.point = point;
			addMarker(point, "Current Point", BitmapDescriptorFactory.HUE_RED);

			text.setText("Point Selected!");
		}
		else {
			
			text.setText("Point Invalid.");
			
		}
		
	}
	
}
