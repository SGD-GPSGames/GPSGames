package edu.virginia.cs.sgd.gpsgames;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.android.gms.maps.model.LatLng;

public class PointPicker {

	//The color of the Google Map which the program accepts
	private static final int ACCEPT = -65794;

	public ArrayList<LatLng> pickPointsInRadius(LatLng center, double minRadius, double radius, int numPoints) {

		ArrayList<LatLng> points = new ArrayList<LatLng>();

		int maxTries = 2 * numPoints;
		Random rand = new Random();

		for(int i = 0; i < maxTries; i++) {

			double rad = rand.nextDouble() * (radius-minRadius) + minRadius;
			double angle = rand.nextDouble() * 2 * Math.PI;

			LatLng point = new LatLng(center.latitude + rad * Math.cos(angle), center.longitude + rad * Math.sin(angle));

			if(isValid(point)) {

				points.add(point);
				if(points.size() == numPoints) {
					return points;
				}

			}

		}

		//TODO Too many tries
		return null;
	}

	public ArrayList<LatLng> pickPointInBounds(ArrayList<LatLng> bounds, int numPoints) {

		ArrayList<LatLng> points = new ArrayList<LatLng>();

		int maxTries = 2 * numPoints;
		Random rand = new Random();

		for(int i = 0; i < maxTries; i++) {
			ArrayList<Triangle> triangles = triangulate(bounds);

			double area = 0;
			for(Triangle t : triangles) {
				area += t.getArea();
			}

			double r = rand.nextDouble() * area;

			double currentNum = 0;
			for(Triangle t : triangles) {

				currentNum += t.getArea();

				if(r <= currentNum) {
					LatLng point = t.getPoint(rand.nextDouble(), rand.nextDouble());

					if(isValid(point)) {

						points.add(point);
						if(points.size() == numPoints) {
							return points;
						}

					}
					else {
						break;
					}
				}

			}
			
		}
		
		// TODO Too many tries
		return null;
	}

	public boolean isValid(LatLng point) {

		BufferedImage b = null;

		try {

			URL loc = new URL(getMapUrl(point.latitude,
					point.longitude, 10, false, false, false, false));
			InputStream in = loc.openStream();
			b = ImageIO.read(in);

		} catch (IOException e) {

			e.printStackTrace();
			return false;

		}

		if(b == null) {
			return false;
		}

		int p = b.getRGB(b.getWidth() / 2, b.getHeight() / 2);

		System.out.println(p);

		return p == ACCEPT;
	}

	private ArrayList<Triangle> triangulate(ArrayList<LatLng> points) {

		if(points.size() < 3) {
			// TODO Too small
			return null;
		}

		if(points.size() == 3) {

			ArrayList<Triangle> triangles = new ArrayList<Triangle>();

			Triangle t = new Triangle(points.get(0), points.get(1), points.get(2));

			triangles.add(t);

			return triangles;
		}

		ArrayList<LatLng> newPoints = new ArrayList<LatLng>();
		newPoints.addAll(points);

		Triangle t = new Triangle(newPoints.get(0), newPoints.get(1), newPoints.get(2));

		newPoints.remove(1);

		ArrayList<Triangle> triangles = triangulate(newPoints);
		triangles.add(t);

		return triangles;
	}

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
		+ "&style=feature:landscape.natural|color:0xffffff"
		+ "&style=feature:landscape.man_made|color:0x000000";
	}

}
