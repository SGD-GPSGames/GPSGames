package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.google.android.gms.maps.model.LatLng;

/**
 * The superclass for all games. Handles all players and collisions
 * 
 * @author Jack
 *
 */
public abstract class Game {
	
	// Whether game is started
	private boolean on;
	
	// The range of tolerance for collisions
	private double tolerance;
	
	// The map of names to points
	private Map<String, Point> points;

	/**
	 * Constructor. Sets the tolerance and instantiates map.
	 * 
	 * @param tolerance
	 */
	public Game(double tolerance) {
		this.on = false;
		this.tolerance = tolerance;
		this.points = new TreeMap<String, Point>();
	}
	
	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	/**
	 * Puts a point in the map
	 * 
	 * @param p The point to insert
	 */
	public void putPoint(Point p) {
		points.put(p.getTitle(), p);
	}

	/**
	 * Looks up a point in the map.
	 * 
	 * @param title The name of the point.
	 * 
	 * @return The point to get.
	 */
	public Point getPoint(String title) {
		return points.get(title);
	}
	
	/**
	 * Returns the points in the map as a list
	 * 
	 * @return An ArrayList of points
	 */
	public ArrayList<Point> getPointList() {
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.addAll(points.values());
		return pointList;
	}
	
	/**
	 * Updates the position of a point
	 * 
	 * @param title The name of the point to change
	 * 
	 * @param newPos The new position
	 */
	public void updatePosition(String title, LatLng newPos) {
		getPoint(title).setPosition(newPos);
	}
	
	/**
	 * Checks if two points are within the tolerance of each other
	 * 
	 * @param p1 The LatLng of the first point
	 * @param p2 The LatLng of the second point
	 * @return true if the two points are within the tolerance of each other.
	 */
	private boolean inRange(LatLng p1, LatLng p2) {
		double dist = Math.hypot(p1.latitude - p2.latitude, p1.longitude - p2.longitude);
		
		return dist < tolerance;
	}
	
	/**
	 * Checks all points for colliding points
	 */
	public void checkCollision() {
		ArrayList<Point> pointList = getPointList();
		
		for(int i = 0; i < pointList.size(); i++) {
			Point p1 = pointList.get(i);
			
			for(int j = i+1; j < pointList.size(); j++) {
				Point p2 = pointList.get(j);
				
				if(inRange(p1.getPosition(), p2.getPosition())) {
					onCollision(p1, p2);
				}
			}
		}
	}

	/**
	 * Called for each client, updating their position and applying any powerups.
	 * 
	 * @param name The name of the client's entity within the game.
	 * 
	 * @param newPos The new position for the client
	 * 
	 * @param p The powerup being applied, or null if no powerup is to be applied.
	 */
	public void clientUpdate(String name, LatLng newPos, Powerup p) {
		
		updatePosition(name, newPos);
		
		if(p != null) {
			applyPowerup(name, p);
		}
	}
	
	/**
	 * Called when two points are colliding.
	 * 
	 * @param p1 The first point
	 * @param p2 The second point
	 */
	public abstract void onCollision(Point p1, Point p2);
	
	/**
	 * Called when a client uses a powerup.
	 * 
	 * @param player The name of the entity to be powered up
	 * 
	 * @param p The powerup being used.
	 */
	public abstract void applyPowerup(String player, Powerup p);
	
}
