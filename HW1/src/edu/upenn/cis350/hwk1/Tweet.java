/**
 * 
 */
package edu.upenn.cis350.hwk1;

/**
 * @author joshross
 *
 */
public class Tweet {
	
	private String state;
	private Time time;
	private String text;

	/**
	 * Construct a Tweet so that it stores the latitude, longitude, time, and text of
	 * the tweet
	 */
	public Tweet(String state, Time time, String text) {
		this.state = state;
		this.time = time;
		this.text = text;
	}
	
	public String getState() {
		return this.state;
	}
	
	public Time getTime() {
		return this.time;
	}
	
	public String getText() {
		return this.text;
	}

}
