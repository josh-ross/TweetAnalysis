/**
 * 
 */
package edu.upenn.cis350.hwk1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author joshross
 *
 */
public class Menu {
	
	@SuppressWarnings("unchecked")
	public static void Rank(ArrayList<Tweet> tweets, Location states) {
		HashMap<String, Integer> stateTweets = new HashMap<String, Integer>();
		
		Iterator<String> stateItr = states.stateCoord.keySet().iterator();
		while (stateItr.hasNext()) {
			stateTweets.put(stateItr.next(), 0);
		}
		
		Iterator<Tweet> tweetItr = tweets.iterator();
		while (tweetItr.hasNext()) {
			Tweet currTweet = tweetItr.next();
			int newVal = stateTweets.get(currTweet.getState()) + 1;
			stateTweets.put(currTweet.getState(), newVal);
		}
		
		List<Entry<String, Integer>> list = sortByValue(stateTweets);
		for (int i = list.size() - 1; i >= 0; i--) {
			System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void Hashtags(ArrayList<Tweet> tweets, String state) {
		Tweet curr;
		HashMap<String, Integer> hashtags = new HashMap<String, Integer>();
		for (int i = 0; i < tweets.size(); i++) {
			curr = tweets.get(i);
			if (curr.getState().equals(state)) {
				//Lowercase the entire piece of text and split it to get the hashtags
				String text = curr.getText().toLowerCase();
				String[] words = text.split(" ");
				for (int j = 0; j < words.length; j++) {
					if (words[j].length() > 0 && words[j].charAt(0) == '#') {
						if (hashtags.containsKey(words[j])) {
							int newVal = hashtags.get(words[j]) + 1;
							hashtags.put(words[j], newVal);
						}
						else {
							hashtags.put(words[j], 1);
						}
					}
				}
			}
		}
		
		List<Entry<String, Integer>> list = sortByValue(hashtags);
		for (int i = list.size() - 1; i >= 0 && i > list.size() - 11; i--) {
			System.out.println(list.get(i).getKey());
		}
	}
	
	public static void PerHour(ArrayList<Tweet> tweets, String phrase) {
		Tweet curr;
		Map<String, Integer> perHour = new HashMap<String, Integer>();
		for (int i = 0; i < tweets.size(); i++) {
			curr = tweets.get(i);
			if (curr.getText().contains(phrase)) {
				if (perHour.containsKey(curr.getTime().toString())) {
					int newVal = perHour.get(curr.getTime().toString()) + 1;
					perHour.put(curr.getTime().toString(), newVal);
				}
				else {
					perHour.put(curr.getTime().toString(), 1);
				}
			}
		}
		
		if (perHour.size() == 0) {
			System.out.println("There are no Tweets containing this phrase");
			return;
		}
		
		List<String> list = new ArrayList<String>(perHour.keySet());
		Collections.sort(list);
		String currTime;
		for (int i = 0; i < list.size(); i++) {
			currTime = list.get(i);
			System.out.println(currTime + " " + perHour.get(currTime) + " times");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List sortByValue(HashMap map) {
		List<Map.Entry> statesVals = new LinkedList<Map.Entry>(map.entrySet());
		Comparator compVals = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return ((Comparable)((Map.Entry) o1).getValue()).compareTo(((Map.Entry) o2).getValue());
			}
		};
		Collections.sort(statesVals, compVals);
		return statesVals;
	}

}
