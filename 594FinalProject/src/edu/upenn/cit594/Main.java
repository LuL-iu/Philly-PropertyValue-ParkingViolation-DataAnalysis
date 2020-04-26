package edu.upenn.cit594;


import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyCSVReader;
import edu.upenn.cit594.datamanagement.ViolationCSVReader;
import edu.upenn.cit594.datamanagement.ViolationJsonReader;
import edu.upenn.cit594.datamanagement.ViolationReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.processor.PropertyProcessor;
import edu.upenn.cit594.processor.ViolationProcessor;
import edu.upenn.cit594.ui.UserInterface;
// check json
/**
 * this is a main class, which checks the form of arguments, read the arguments,  create other objects and their relationships, then start 
 * the application via the UI.
 *  @author Lu & Kai
 */

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length != 5) {
			System.out.println("Error, please enter argumnets again");
			System.exit(0);
		}
		String format = args[0];
		format = format.toLowerCase();
		if(!format.equals("json") && !format.equals("csv")){
			System.out.println("Error, please enter right file format");
			System.exit(0);
		}
		
		String parkingFile = args[1];
		String propertyFile = args[2];
		String populationFile = args[3];
		String logFile = args[4];
		
		PropertyCSVReader propertyReader = new PropertyCSVReader(propertyFile);
		PopulationReader populationReader = new PopulationReader(populationFile);
		ViolationReader violationReader = null;
		
		//create the log file with file name provided by arguments
//		GlobalName logName = GlobalName.getInstance();
//		logName.setName(logFile);
		Logger logger = Logger.getInstance();
//		logger.makeFile(logFile);
		logger.makeFile(logFile);
		String arguments = "";
		for(String s : args) {
			arguments += s + " ";
		}	
		logger.log(arguments);
		
		//create parkingFine Reader "txt" or "json" format
		if(format.equals("csv")) {
			violationReader = new ViolationCSVReader(parkingFile);
		}
		else if(format.equals("json")) {
			violationReader = new ViolationJsonReader(parkingFile);
		}
		
		ViolationProcessor vProcessor = new ViolationProcessor(violationReader);
		PopulationProcessor poProcessor = new PopulationProcessor(populationReader);
		PropertyProcessor prProcessor = new PropertyProcessor(propertyReader);
		UserInterface ui = new UserInterface(poProcessor, vProcessor, prProcessor);
		ui.start();

	}

}
