package edu.virginia.cs.sgd.gpsgames;

import java.util.Calendar;

public class GameThread extends Thread {

	// The race
	private Game game;

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
	public GameThread(Game game) {
		super();
		this.game = game;
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
			game.update(date);
		}

	}

	/**
	 * Sets the thread to stop before the next step
	 */
	public void stopThread() {

		stop = true;
		
	}
}
