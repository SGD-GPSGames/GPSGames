package edu.virginia.cs.sgd.gpsgames;

import com.google.android.gms.maps.model.LatLng;

public class Point {

	private LatLng position;
	private String title;
	private float color;
	
	public Point(LatLng position, String title, float color) {
		super();
		this.position = position;
		this.title = title;
		this.color = color;
	}
	
	public LatLng getPosition() {
		return position;
	}
	public void setPosition(LatLng position) {
		this.position = position;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getColor() {
		return color;
	}
	public void setColor(float color) {
		this.color = color;
	}
	
	
}
