package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyCSVReader;
import edu.upenn.cit594.datamanagement.ViolationCSVReader;
import edu.upenn.cit594.datamanagement.ViolationJsonReader;
import edu.upenn.cit594.datamanagement.ViolationReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.StrategyChooser;
import edu.upenn.cit594.processor.ViolationProcessor;
import edu.upenn.cit594.ui.UserInterface;

/**
 * this is a main class, which checks the form of arguments, read the arguments,
 * create other objects and their relationships, then start the application via
 * the UI.
 * 
 * @author Lu & Kai
 * 
 */

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 5) {
			System.out.println("Error, please enter arguments again");
			System.exit(0);
		}
		// create the log file with file name provided by arguments -- I moved up since
		// suppose to log everything! eve
		Logger logger = Logger.getInstance();
		logger.makeFile(args[4]);

		// combine arguments to string
		String arguments = "";
		for (String s : args) {
			arguments += s + " ";
		}
		logger.log(arguments);

		String format = args[0];
		format = format.toLowerCase();
		if (!format.equals("json") && !format.equals("csv")) {
			System.out.println("Error, please enter right file format");
			System.exit(-1);
		}

		String parkingFile = args[1];
		String propertyFile = args[2];
		String populationFile = args[3];

		// create reader
		PropertyCSVReader propertyReader = new PropertyCSVReader(propertyFile);
		PopulationReader populationReader = new PopulationReader(populationFile);
		ViolationReader violationReader = null;

		// create parkingFine Reader "txt" or "json" format
		if (format.equals("csv")) {
			violationReader = new ViolationCSVReader(parkingFile);
		} else if (format.equals("json")) {
			violationReader = new ViolationJsonReader(parkingFile);
		}

		// create processor
		ViolationProcessor vProcessor = new ViolationProcessor(violationReader);
		PopulationProcessor poProcessor = new PopulationProcessor(populationReader);
		// PropertyProcessor prProcessor = new PropertyProcessor(propertyReader);
		StrategyChooser prProcessor = new StrategyChooser(propertyReader); // StrategyPattern Implementation

		// creater ui and start
		UserInterface ui = new UserInterface(poProcessor, vProcessor, prProcessor);
		ui.start();

	}

}
