package edu.upenn.cit594.logging;

import java.io.File;
import java.io.PrintWriter;

/**
 * this is class which uses singleton pattern, it creates logger instance and have the methods that write to log file.
 *
 */

public class Logger3 {
	private PrintWriter out;
	
	private Logger3() {
		try { 
			GlobalName gName = GlobalName.getInstance();
			String fName = gName.getName(); 
			out = new PrintWriter(new File(fName));
		}
		catch (Exception e) { };
	}
	
	private static Logger3 instance = new Logger3();
	
	public static Logger3 getInstance() {
		return instance; 
	}
	
	public void log(String msg) {
		out.println(msg);
		out.flush();
	}

}
