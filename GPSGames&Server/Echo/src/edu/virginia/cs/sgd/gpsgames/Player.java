package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

public abstract class Player {

	private String name;
	private Point point;
	private Game<?> g;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Game<?> getG() {
		return g;
	}

	public void setG(Game<?> g) {
		this.g = g;
	}
	
	public Point getPoint() {
		return point;
	}

	public void setP(Point point) {
		this.point = point;
	}

	public void updateMap(ArrayList<Point> points) {
		
	}
	
	public void message(String msg) {
		
	}
	
}
