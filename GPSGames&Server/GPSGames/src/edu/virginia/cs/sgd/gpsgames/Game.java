package edu.virginia.cs.sgd.gpsgames;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Abstract superclass for all activities involving Google Maps
 * 
 * @author Jack
 *
 */
public abstract class Game extends Activity {

	protected MapView mapView;
	protected GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());

		mapView = (MapView) findViewById(R.id.map);

		mapView.onCreate(savedInstanceState);

		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map = mapView.getMap();
		
		map.setOnMapClickListener(new OnMapClickListener() {

			public void onMapClick(LatLng point) {
				
				onClick(point);
				
			}
			
		});
		
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		setUpUI();
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

	protected void addMarker(LatLng position, String title, float color) { 
		@SuppressWarnings("unused")
		Marker marker = map.addMarker(new MarkerOptions()
		.position(position)
		.title(title)
		.icon(BitmapDescriptorFactory.defaultMarker(color)));
		
	}
	
	public abstract int getLayout();
	
	public abstract void setUpUI();
	
	public abstract void onClick(LatLng point);
}

