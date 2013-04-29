package edu.virginia.cs.sgd.gpsgames;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.thinkijustwon.nosockrocks.user.UserThread;

/**
 * The superclass for all games. Handles all players and collisions
 * 
 * @author Jack
 *
 */
public abstract class Game<P extends Player> extends com.thinkijustwon.nosockrocks.game.Game {

	// The range of tolerance for collisions
	public static final double tolerance = .01;
	
	// TODO Name, final string
	public String name;
	public String gameType;
	
	// Whether game is started
	private boolean on;
	
	// List of players in game
	private ArrayList<P> players;
	
	// Other points
	private ArrayList<Point> otherPoints;

	/**
	 * Constructor. Sets the tolerance and instantiates map.
	 * 
	 * @param tolerance
	 */
	public Game(UserThread creator, int gameID, String gameType, String name) {
		super(creator, gameID);
		this.gameType = gameType;
		this.name = name;
		this.on = false;
		this.players = new ArrayList<P>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public void addPlayer(P player) {
		players.add(player);
	}
	
	public void addPoint(Point p) {
		otherPoints.add(p);
	}

	
	/**
	 * Returns the points in the map as a list
	 * 
	 * @return An ArrayList of points
	 */
	public ArrayList<Point> getPointList() {
		ArrayList<Point> pointList = new ArrayList<Point>();
		for(P player : players) {
			pointList.add(player.getPoint());
		}
		pointList.addAll(otherPoints);
		return pointList;
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
		
		for(int i = 0; i < players.size(); i++) {
			P player = players.get(i);
			
			for(int j = i+1; j < players.size(); j++) {
				P player2 = players.get(i);
				
				if(inRange(player.getPoint().getPosition(), player2.getPoint().getPosition())) {
					playerCollision(player, player2);
				}
			}
			
			for(Point point : otherPoints) {

				if(inRange(player.getPoint().getPosition(), point.getPosition())) {
					onCollision(player, point);
				}
			}
			
		}
	}
	
	public void postGame() {
	
		for(P player : players) {
			player.updateMap(getPointList());
		}
	}

	/**
	 * Called when two players are colliding.
	 * 
	 * @param player The player
	 * @param player2 The second player
	 */
	public abstract void playerCollision(P player, P player2);
	
	/**
	 * Called when a player and another point are colliding.
	 * 
	 * @param player The player
	 * @param point The second point
	 */
	public abstract void onCollision(P player, Point point);
	
	/**
	 * Called when a client uses a powerup.
	 * 
	 * @param player The name of the entity to be powered up
	 * 
	 * @param p The powerup being used.
	 */
	public abstract void applyPowerup(P player, Powerup p);
	
	public String toString() {
		return getID() + "," + gameType +"," + name + ";";
	}
	
}
