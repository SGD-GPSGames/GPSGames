package edu.virginia.cs.sgd.gpsgames.util;

public class Util {
	
		/**
		 * Thread workaround for service
		 * Make sure you clean up your threads!
		 * @param runnable
		 * @return
		 */
	  public static Thread performOnBackgroundThread(final Runnable runnable) {
		    final Thread t = new Thread() {
		        @Override
		        public void run() {
		            try {
		                runnable.run();
		            } finally {

		            }
		        }
		    };
		    t.start();
		    return t;
	}
}