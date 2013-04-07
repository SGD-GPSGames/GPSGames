package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	private MapView mapView;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		mapView = (MapView) findViewById(R.id.map);

		mapView.onCreate(savedInstanceState);

		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map = mapView.getMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	public LatLng getCurrentLocation() {
		Location l = map.getMyLocation();
		
		return new LatLng(l.getLatitude(), l.getLongitude());
	}
	
	private void addMarker(LatLng position, String title, float color) { 
		@SuppressWarnings("unused")
		Marker marker = map.addMarker(new MarkerOptions()
		.position(position)
		.title(title)
		.icon(BitmapDescriptorFactory.defaultMarker(color)));
		
	}

	public void update(ArrayList<Point> points) {
		
		map.clear();
		
		for(Point p : points) {
			addMarker(p.getPosition(), p.getTitle(), p.getColor());
		}
		
	}
	
	
}
