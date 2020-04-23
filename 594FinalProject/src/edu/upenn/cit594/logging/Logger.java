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

	public void makeFile(String filenameFromUser) {
		filename = filenameFromUser;

		try {
			File f = new File(filename);

			// check if file exists
			if (f.exists() && !f.isDirectory()) {
				FileWriter fwri = new FileWriter(f, true);
				BufferedWriter bwri = new BufferedWriter(fwri);
				// PrintWriter pwri = new PrintWriter(bwri);
				out = new PrintWriter(bwri);
				logTime();
			} else {
				// create a new file
				File Fileright = new File(filename);
				out = new PrintWriter(Fileright); // new File(filename));
				logTime();
			}
		} catch (Exception e) {
			System.out.println("ERROR: in logger:  NO FILE CREATED");
		}
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

	private void logTime () {
		 out.println(System.currentTimeMillis() + "\n");
	}
	
	public void log(String msg) {
		out.println(msg);
		out.flush();
	}
}
