package edu.upenn.cit594.logging;

import java.io.File;
import java.io.PrintWriter;

/**
 * this is class which uses singleton pattern, it creates logger instance and have the methods that write to log file.
 *
 */

public class Logger {
	private PrintWriter out;
	
	private Logger() {
		try { 
			GlobalName gName = GlobalName.getInstance();
			String fName = gName.getName(); 
			out = new PrintWriter(new File(fName));
		}
		catch (Exception e) { };
	}
	
	private static Logger instance = new Logger();
	
	public static Logger getInstance() {
		return instance; 
	}
	
	public void log(String msg) {
		out.println(msg);
		out.flush();
	}

}
