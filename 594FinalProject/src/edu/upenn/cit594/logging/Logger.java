package edu.upenn.cit594.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * creates the logger file uses Singleton method only one instance can be
 * created
 * 
 * @author Kai and Lu
 *
 */
public class Logger {
	private static String filename;
	private PrintWriter out;

	private Logger() {
	}

	

	public void makeFile(String filenameFromUser, String parkViolationFormat, String
			parkingViolFile, String propertyValFile, String popFile, String logFile) {
		
		filename = filenameFromUser;

		try {
			File f = new File(filename);

			// check if file exists
			//File file =new File("C://myfile.txt");
	    	  if(!f.exists()){
	    	 	f.createNewFile();
	    	  }
	    	  FileWriter fw = new FileWriter(f,true);
	    	  BufferedWriter bw = new BufferedWriter(fw);
	    	   out = new PrintWriter(bw);
	          //helps add new line to file content
	    	  out.println("");
		} catch (Exception e) {
			System.out.println("ERROR: in logger:  NO FILE CREATED");
		}
		programStart(parkViolationFormat,
				parkingViolFile,  propertyValFile,  popFile,  logFile);
	}

	// method to print filename [mainly for testing]
	public void printFileName() {
		System.out.println(filename);
	}

	private String getFileName() {
		return filename;
	}

	private static Logger instance = new Logger();

	public static Logger getInstance() {
		return instance;
	}

	private void programStart (String parkViolationFormat, String
			parkingViolFile, String propertyValFile, String popFile, String logFile) {
		 out.println(System.currentTimeMillis() + " " + parkViolationFormat + " " + parkingViolFile
				 +" " + propertyValFile + " " + popFile + logFile);
	}
	
	
	public void newInputFileRead(String fileOpened) {
		 out.println(System.currentTimeMillis() + " " + fileOpened);
	}
	
	// will log zipcode and selection
	public void log(String msg) {
		out.println(System.currentTimeMillis() + " " + msg);
		out.flush();
	}
}
