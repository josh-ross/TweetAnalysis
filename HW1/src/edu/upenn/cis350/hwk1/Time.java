/**
 * 
 */
package edu.upenn.cis350.hwk1;

/**
 * @author joshross
 *
 */
public class Time implements Comparable<Time> {
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private int sec;

	public Time(int year, int month, int day, int hour, int min, int sec) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getSec() {
		return this.sec;
	}
	
	@Override
	public String toString() {
		return this.year + "-" + this.month + "-" + this.day + " " + this.hour + ":00";
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Time)) {
			return false;
		}
		
		Time t = (Time) o;
		
		return (this.year == t.getYear() && this.month == t.getMonth() && this.day == t.getDay() && this.hour == t.getHour());
	}

	@Override
	public int compareTo(Time that) {
		if (this.year < that.getYear()) {
			return -1;
		}
		else if (this.year > that.getYear()) {
			return 1;
		}
		else {
			if (this.month < that.getMonth()) {
				return -1;
			}
			else if (this.month > that.getMonth()) {
				return 1;
			}
			else {
				if (this.day < that.getDay()) {
					return -1;
				}
				else if (this.day > that.getDay()) {
					return 1;
				}
				else {
					if (this.hour < that.getHour()) {
						return -1;
					}
					else if (this.hour > that.getHour()) {
						return 1;
					}
					else {
						return 0;
					}
				}
			}
		}
	}
}
