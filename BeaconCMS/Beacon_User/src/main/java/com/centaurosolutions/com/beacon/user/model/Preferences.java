/**
 * 
 */
package com.centaurosolutions.com.beacon.user.model;
/**
 * @author Centauro
 *
 */
public class Preferences {
String preference;
String state;

/**
 * 
 */
public Preferences() {
	// TODO Auto-generated constructor stub
}


/**
 * @param preference
 * @param state
 */
public Preferences(String preference, String state) {
	super();
	this.preference = preference;
	this.state = state;
}



/**
 * @return the preference
 */
public String getPreference() {
	return preference;
}



/**
 * @param preference the preference to set
 */
public void setPreference(String preference) {
	this.preference = preference;
}



/**
 * @return the state
 */
public String getState() {
	return state;
}



/**
 * @param state the state to set
 */
public void setState(String state) {
	this.state = state;
}
}
