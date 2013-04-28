package edu.virginia.cs.sgd.gpsgames;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class SelectAreaActivity extends MapActivity {

	private String name;
	private Polygon points;
	private Button select;
	private Button clear;

	@Override
	public int getLayout() {
		return R.layout.activity_select_area;
	}

	@Override
	public void setUpUI() {
		select = (Button) findViewById(R.id.point_select_button);

		select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				moveToOldActivity();
			}

		});
		
		clear = (Button) findViewById(R.id.point_clear_button);

//		clear.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				map.clear();
//				points = null;
//			}
//
//		});

		Bundle ex = getIntent().getExtras();
		
		String str = ex.getString("Name");
		Object obj = ex.get("Points");

		name = (str == null ? "" : str);

		double[][] pointArr = (obj == null ? null : (double[][]) obj);

		if (pointArr == null) {
			return;
		}
		
		if(pointArr.length != 0) {

			PolygonOptions p = new PolygonOptions();

			for(double[] point : pointArr) {
				p.add(new LatLng(point[0], point[1]));
			}

			p.strokeColor(Color.RED);
			p.fillColor(Color.RED);

			points = map.addPolygon(p);
			mapView.invalidate();
		}
	}

	@Override
	public void onClick(LatLng point) {

		if(points == null) {
			points = map.addPolygon(new PolygonOptions()
			.strokeColor(Color.RED)
			.fillColor(Color.RED)
			.add(point));
		}
		else {
			List<LatLng> list = points.getPoints();
			list.add(0, point);
			points.setPoints(list);
		}
		
	}

	public void moveToOldActivity() {

		//REPLACE MenuActivity.class BELOW WITH CLASS YOU WANT TO GO TO
		Intent mainActivity = new Intent(this, TreasureSetupActivity.class);

		mainActivity.putExtra("Name", name);

		List<LatLng> list = points.getPoints();
		double[][] pointArr = new double[list.size()][2];

		for(int i = 0; i < list.size(); i++) {
			pointArr[i][0] = list.get(i).latitude;
			pointArr[i][1] = list.get(i).longitude;
		}
		mainActivity.putExtra("Points", pointArr);

		map.clear();
		points = null;

		startActivity(mainActivity);
	}

}
