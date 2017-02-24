/**
 * 
 */
package edu.upenn.cis350.hwk1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author joshross
 *
 */
public class Location {
	
	private class LatLong {
		
		double lat;
		double lon;
		
		public LatLong(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
		
		public double getLat() {
			return this.lat;
		}
		
		public double getLon() {
			return this.lon;
		}
	}
	
	Map<String, LatLong> stateCoord = new HashMap<String, LatLong>();
	
	public void addState(String name, double latit, double longi) {
		stateCoord.put(name, new LatLong(latit, longi));
	}
	
	//Use Euclidean Geometry to find the state that the coordinates are within
	public String findState(double lat, double lon) {
		String closeState = "";
		double minDistance = Double.MAX_VALUE;
		LatLong currCords;
		double currDist;
		Entry<String, LatLong> currElement;
		
		Iterator<Entry<String, LatLong>> itr = stateCoord.entrySet().iterator();
		while (itr.hasNext()) {
			currElement = itr.next();
			currCords = currElement.getValue();
			double x = Math.pow(currCords.getLat() - lat, 2);
			double y = Math.pow(currCords.getLon() - lon, 2);
			currDist = Math.sqrt(x + y);
			if (minDistance > currDist) {
				minDistance = currDist;
				closeState = currElement.getKey();
			}
		}
		return closeState;
	}

}
