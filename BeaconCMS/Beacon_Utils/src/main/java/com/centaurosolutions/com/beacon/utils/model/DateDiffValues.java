/**
 * 
 */
package com.centaurosolutions.com.beacon.utils.model;

/**
 * @author Eduardo
 *
 */
public class DateDiffValues {
	
	

    
	/**
	 * @param days
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public DateDiffValues(int days, int hours, int minutes, int seconds) {
		super();
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	/**
	 * 
	 */
	public DateDiffValues() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(int days) {
		this.days = days;
	}

	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * @return the seconds
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	int days;
	int hours;
	int minutes;
	int seconds;

}
