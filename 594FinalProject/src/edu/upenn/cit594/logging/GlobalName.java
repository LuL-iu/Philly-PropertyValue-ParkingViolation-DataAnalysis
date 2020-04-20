package edu.upenn.cit594.logging;

/**
 * this is class which uses singleton pattern, it can help to set up the log file name
 *
 */


public class GlobalName {
	private String name;
	private static GlobalName gName = new GlobalName();
	
	private GlobalName() {
	}
	
	public static GlobalName getInstance() {
		return gName;
	}
	
	public  void setName(String text) {
		name = text;
	}
	
	public String getName() {
		return name;
	}
	
}
