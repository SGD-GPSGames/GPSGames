package edu.virginia.cs.sgd.gpsgames;

import com.google.android.gms.maps.model.LatLng;

/**
 * For use with MapReader class.
 * 
 * @author Jack
 *
 */
public interface PointRequestor {

	/**
	 * Called when the point validity is established
	 * 
	 * @param point The point being checked
	 * 
	 * @param valid true if the point is valid; false otherwise.
	 */
	void pointCheck(LatLng point, boolean valid);
	
}
