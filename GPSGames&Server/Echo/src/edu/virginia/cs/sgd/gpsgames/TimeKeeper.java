package edu.virginia.cs.sgd.gpsgames;

/**
 * Determines elapsed time.
 * 
 * @author John McNamara
 *
 */
public class TimeKeeper {

	// Whether or not the clock is currently accumulating time
	private boolean on;
	
	//The time stored from pauses
	private long storedTime;
	
	//The last call to nanotime. Used to calculate difference
	private long last;
	
	/**
	 * Default constructor. Sets all values to 0 and false.
	 */
	public TimeKeeper() {
		on = false;
		storedTime = 0;
		last = 0;
	}
	
	/**
	 * Sets the stored time of the timer to the input value.
	 * 
	 * @param time The time in nanoseconds.
	 */
	public void setTime(long time) {
		this.storedTime = time;
	}
	
	/**
	 * Determines whether the timer is currently running
	 * 
	 * @return true if the timer is on; false otherwise
	 */
	public boolean isOn() {
		return on;
	}
	
	/**
	 * Starts the timer.
	 */
	public void start() {
		last = System.nanoTime();
		on = true;
	}
	
	/**
	 * Gets the current elapsed time.
	 * 
	 * @return A long representing the elapsed time in nanoseconds.
	 */
	private long getTime() {
		if(on) {
			return System.nanoTime() - last + storedTime;
		}
		else {
			return storedTime;
		}
	}
	
	/**
	 * Gets the time in seconds. May lose precision.
	 * 
	 * @return A double representing the elapsed time in fractions of a second.
	 */
	public double getTimeSeconds() {
		return ((double)getTime()) / 1E9;
	}
	
	/**
	 * Pauses the timer, but holds its current elapsed time.
	 */
	public void stop() {
		storedTime = getTime();
		on = false;
	}
	
	/**
	 * Resets the timer, losing all elapsed time.
	 */
	public void reset() {
		on = false;
		storedTime = 0;
		last = 0;
	}

}
