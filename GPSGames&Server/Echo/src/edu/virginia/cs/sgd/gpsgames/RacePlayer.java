package edu.virginia.cs.sgd.gpsgames;


public class RacePlayer extends Player {

	private boolean ready;
	private boolean racing;
	private TimeKeeper clock;

	
	public RacePlayer(String name, Race g) {
		super(name, g);
		this.ready = false;
		this.racing = false;
		this.clock = new TimeKeeper();
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public TimeKeeper getClock() {
		return clock;
	}

	public void setClock(TimeKeeper clock) {
		this.clock = clock;
	}

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
