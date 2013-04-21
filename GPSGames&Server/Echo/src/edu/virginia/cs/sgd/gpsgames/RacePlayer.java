package edu.virginia.cs.sgd.gpsgames;


public class RacePlayer extends Player {

	private boolean racing;
	private TimeKeeper clock;

	public boolean isRacing() {
		return racing;
	}

	public void setRacing(boolean racing) {
		this.racing = racing;
	}

	public void start() {
		clock.start();
	}

	public void stop() {
		clock.stop();
	}
	
	public double getTime() {
		return clock.getTimeSeconds();
	}
	
}
