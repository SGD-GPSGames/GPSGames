package edu.virginia.cs.sgd.gpsgames;


import com.google.android.gms.maps.model.LatLng;

public class Triangle {

	private LatLng a;
	private LatLng b;
	private LatLng c;
	
	private double area;

	public Triangle(LatLng a, LatLng b, LatLng c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		
		calcArea();
	}

	private void calcArea() {
		area = Math.abs((a.latitude * (b.longitude - c.longitude) + b.latitude * (c.longitude - a.longitude) + c.latitude * (a.longitude - b.longitude))/2);
	}
	
	public double getArea() {
		return area;
	}
	
	public LatLng getPoint(double r1, double r2) {
		
		double tmp = Math.pow(r1, .5);
		
		return new LatLng((1-tmp)*a.latitude+(tmp*(1-r2))*b.latitude+(tmp*r2)*c.latitude,(1-tmp)*a.longitude+(tmp*(1-r2))*b.longitude+(tmp*r2)*c.longitude);
	}
	
	
}
