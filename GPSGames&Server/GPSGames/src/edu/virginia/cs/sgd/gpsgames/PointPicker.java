package edu.virginia.cs.sgd.gpsgames;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

public class PointPicker {

	private PointRequestor source;
	
	public PointPicker(PointRequestor source) {
		super();
		this.source = source;
	}

	public void pickPoint(LatLng point) {

		FetchStaticMap fetch = new FetchStaticMap();
		fetch.execute(point);

	}

	private void onCheck(LatLng point, boolean valid) {

		source.pointCheck(point, valid);

	}

	private class FetchStaticMap extends AsyncTask<LatLng, Object, Bitmap> {

		//The color of the Google Map which the program accepts
		private static final int ACCEPT = -65794;
		
		private LatLng point;

		private String getMapUrl(double lat, double lon, int radius, boolean water, boolean highway, boolean arterial, boolean local) {

			final String coordPair = lat + "," + lon;
			return "http://maps.googleapis.com/maps/api/staticmap?" + "&zoom=20"
			+ "&size=" + radius + "x" + radius
			+ "&maptype=roadmap&sensor=true" + "&center=" + coordPair
			+ "&style=element:labels|visibility:off"
			+ "&style=feature:water|color:" + (water ? "0xffffff" : "0x000000")
			+ "&style=feature:road.highway|color:" + (highway ? "0xffffff" : "0x000000")
			+ "&style=feature:road.arterial|color:" + (arterial ? "0xffffff" : "0x000000")
			+ "&style=feature:road.local|color:" + (local ? "0xffffff" : "0x000000")
			+ "&style=feature:poi|color:0xffffff"
//			+ "&style=feature:landscape.natural|color:0xffffff"
//			+ "&style=feature:landscape.man_made|color:0xffffff"; 
			+ "&style=feature:landscape|color:0xffffff";  
		}
		
		private boolean getValidity(Bitmap b) {

			if(b == null) {
				return false;
			}
			
			int p = b.getPixel(b.getWidth() / 2, b.getHeight() / 2);

			return p == ACCEPT;
		}
		
		@Override
		protected Bitmap doInBackground(LatLng... params) {

			Bitmap b = null;

			try {
				point = params[0];

				URL loc = new URL(getMapUrl(point.latitude,
						point.longitude, 10, false, false, false, false));
				InputStream in = loc.openStream();
				b = BitmapFactory.decodeStream(in);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return b;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			onCheck(point, getValidity(result));
		}
	}

}
