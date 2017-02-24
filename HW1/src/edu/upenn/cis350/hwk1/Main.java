/**
 * 
 */
package edu.upenn.cis350.hwk1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author joshross
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("The number of runtime arguments is incorrect");
			return;
		}
		
		//Make all of the files
		File tweetFile = new File(args[0]);
		File stateFile = new File(args[1]);
		File log = new File(args[2]);
		
		//Test all the potential errors and terminate the program if they are come across
		if (!tweetFile.exists()) {
			System.out.println("The specified file of tweetFile does not exist");
			return;
		}
		if (!stateFile.exists()) {
			System.out.println("The specified file of stateFile does not exist");
			return;
		}
		if (!tweetFile.canRead()) {
			System.out.println("The specified file of tweetFile cannot be read");
			return;
		}
		if (!stateFile.canRead()) {
			System.out.println("The specified file of stateFile cannot be read");
			return;
		}
		if (!log.canWrite()) {
			System.out.println("The specified log file cannot be opened for writing");
		}
		
		try {
			//Handle the stateFile and put all the data into a location object
			Location states = new Location();
			
			FileReader stateFR = new FileReader(stateFile);
			BufferedReader stateBR = new BufferedReader(stateFR);
			
			String currState;
			while ((currState = stateBR.readLine()) != null) {
				String[] lineArr = currState.split(",");
				String stateName = lineArr[0];
				double lat = Double.parseDouble(lineArr[1]);
				double lon = Double.parseDouble(lineArr[2]);
				states.addState(stateName, lat, lon);
			}
			stateBR.close();
			
			//Handle the tweetFile and put all the appropriate data into an array of tweet objects
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			
			FileReader tweetFR = new FileReader(tweetFile);
			BufferedReader tweetBR = new BufferedReader(tweetFR);
			
			String currTweet;
			while ((currTweet = tweetBR.readLine()) != null) {
				String[] lineArr = currTweet.split("\t");
				String latLong = lineArr[0];
				String timeFull = lineArr[2];
				String text = lineArr[3];
				
				//Handle the coordinates in order to find the state in which the tweet is
				String[] coordinates = latLong.split(",");
				double lat = Double.parseDouble(coordinates[0].substring(1));
				double lon = Double.parseDouble(coordinates[1].substring(0, coordinates[1].length() - 1));
				String stateName = states.findState(lat, lon);
				
				//Handle the time in order to parse it out and create a time object
				String[] timeSplit = timeFull.split(" ");
				String[] date = timeSplit[0].split("-");
				String[] hourMinSec = timeSplit[1].split(":");
				int year = Integer.parseInt(date[0]);
				int month = Integer.parseInt(date[1]);
				int day = Integer.parseInt(date[2]);
				int hour = Integer.parseInt(hourMinSec[0]);
				int min = Integer.parseInt(hourMinSec[1]);
				int sec = Integer.parseInt(hourMinSec[2]);
				Time tweetTime = new Time(year, month, day, hour, min, sec);
				
				//Create the tweet
				Tweet fullTweet = new Tweet(stateName, tweetTime, text);
				tweets.add(fullTweet);
				
			}
			tweetBR.close();
			
			//Allow the log file to be written on
			FileWriter logFW = new FileWriter(log, true);
			BufferedWriter logBW = new BufferedWriter(logFW);
			
			//Log the current time into the log file via: System.currentTimeMillis()
			logBW.write(System.currentTimeMillis() + ": " + args[0] + " " + args[1] + " " + args[2]);
			logBW.newLine();
			
			//Now we want to prompt the user with the main menu and ask what they would like to do
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("Main Menu (please make your selection by typing a number 1-4): \n "
						+ "1. Rank the states by number of tweets \n 2. Show the most popular hashtags in a given state "
						+ "\n 3. Show the number of tweets per hour containing a given term \n 4. Quit the program");
				String option = reader.readLine();
				
				//Log the current time and the users choice into the log file
				logBW.write(System.currentTimeMillis() + ": User Option " + option);
				logBW.newLine();
				
				if (option.equals("1")) {
					Menu.Rank(tweets, states);
				}
				else if (option.equals("2")) {
					String searchState;
					while (true) {
						System.out.println("Enter the name of the state you would like to search: ");
						searchState = reader.readLine();
						
						//Log the current time and the selected state
						logBW.write(System.currentTimeMillis() + ": " + searchState);
						logBW.newLine();
						
						Set<String> stateNames = states.stateCoord.keySet();
						if (stateNames.contains(searchState)) {
							break;
						}
						else {
							System.out.println(searchState + " is not a valid state");
						}
					}
					Menu.Hashtags(tweets, searchState);
				}
				else if (option.equals("3")) {
					System.out.println("Enter the phrase you would like to search for: ");
					String phrase = reader.readLine();
					
					//Log the current time and the selected phrase
					logBW.write(System.currentTimeMillis() + ": " + phrase);
					logBW.newLine();
					
					Menu.PerHour(tweets, phrase);
				}
				else if (option.equals("4")) {
					reader.close();
					
					//Log the current time and "Program Ended"
					logBW.write(System.currentTimeMillis() + ": Program Ended");
					logBW.newLine();
					logBW.close();
					
					return;
				}
				else {
					System.out.println(option + " is not a valid selection");
					continue;
				}
			}		
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
			
			//Allow the log file to be written on
			FileWriter logFW;
			try {
				logFW = new FileWriter(log, true);
				BufferedWriter logBW = new BufferedWriter(logFW);
				
				//Log the current time and the error message
				logBW.write(System.currentTimeMillis() + ": " + e.toString());
				logBW.close();
			} 
			catch (IOException e1) {
				System.out.println(e1.toString());
			}

			return;
		} 
		catch (IOException e) {
			System.out.print(e.toString());
			
			//Allow the log file to be written on
			FileWriter logFW;
			try {
				logFW = new FileWriter(log, true);
				BufferedWriter logBW = new BufferedWriter(logFW);
				
				//Log the current time and the error message
				logBW.write(System.currentTimeMillis() + ": " + e.toString());
				logBW.close();
			} 
			catch (IOException e1) {
				System.out.println(e1.toString());
			}
			
			return;
		}
		
	}

}
