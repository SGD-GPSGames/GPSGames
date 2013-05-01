package edu.virginia.cs.sgd.gpsgames;

import java.util.Calendar;

public class TimeThread extends Thread {

	// The race
	private Race race;

	// Whether to stop the program before the next step
	private boolean stop;

	/**
	 * Constructor. Sets up AI Thead
	 * 
	 * @param race
	 *            The race to update the time with
	 * 
	 * @param stop
	 *            true if the program should stop; false otherwise
	 * 
	 */
	public TimeThread(Race race) {
		super();
		this.race = race;
		this.stop = false;
	}

	@Override
	public void run() {
		
		int[] date = { Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
				Calendar.getInstance().get(Calendar.HOUR),
				Calendar.getInstance().get(Calendar.MINUTE),
				Calendar.getInstance().get(Calendar.SECOND) };
		
		while (!stop) {
			race.timeCheck(date);
		}

	}

	/**
	 * Sets the thread to stop before the next step
	 */
	public void stopThread() {

		stop = true;
		
	}
}
