package edu.upenn.cit594.ui;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.ErrorChecker;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;
import edu.upenn.cit594.processor.ViolationProcessor;

/**
 * this is user interface class, it is responsible for the interacting with user and processing data as it relates to display it.
 * @author Lu & Kai
 */

public class UserInterface {
	protected PopulationProcessor populationProcessor;
	protected ViolationProcessor violationProcessor;
	protected PropertyProcessor propertyProcessor;
	protected Scanner in;
	protected ErrorChecker EChecker = new ErrorChecker();
	protected Map<String, Integer> populationMap;
	
	public UserInterface(PopulationProcessor populationProcessor, ViolationProcessor violationProcessor, PropertyProcessor propertyProcessor) {
		this.populationProcessor = populationProcessor;
		this.violationProcessor = violationProcessor;
		this.propertyProcessor = propertyProcessor;
		in = new Scanner(System.in);
	}
	
	public void start() {
		//display options to screen;
		System.out.println("Enter 0 to Exit\nEnter 1 to show total population for all ZIP codes\n"
				+ "Enter 2 to show total parking fines per capita for each ZIP codes\n"
				+ "Enter 3 to show average market value for residents in specified ZIP code\n"
				+ "Enter 4 to show average total livable area for residents in specified ZIP code\n"
				+ "Enter 5 to show total residential market value per capita for specifed ZIP code\n"
				+ "Enter 6 to show the total residential livable area Per Capita in zip code with highest total parking fine ");
		
		//set up data map in processor(start to read data from the 3 files)
		populationProcessor.buildMap();
		violationProcessor.buildMap();
		propertyProcessor.buildMap();
		populationMap = populationProcessor.getPopulationMap();
		
		//run different methods based on user input
		while(true) {
			String choice = in.next();
			Logger logger = Logger.getInstance();
			logger.log(choice);
			if(choice.equals("0")) {
				break;
			}
			
			else if(choice.equals("1")) {
				displayTotalPopulation();
			}
			
			else if(choice.equals("2")) {
				displayParkingFinePerCapita();
			}
			
			else if(choice.equals("3")) {
				displayAverageResidentialMarketValue();
			}
			
			else if(choice.equals("4")) {
				displayAverageResidentialTotalLivableArea();
			}
			
			else if(choice.equals("5")) {
				displayTotalResidentialMarketValuePerCapita();
			}
			
			else if(choice.equals("6")) {
				displayTotalResidentialLivableAreaPerCapitaInHighestFineLocation();
			}
			
			else {
				System.out.println("Error Input, program will exit");
				//break;
				System.exit(0);
			}
		}
		in.close();
	}
	
	//display total population to screen
	protected void displayTotalPopulation() { 
		int totalPopulation = populationProcessor.totalPopulation();
		System.out.println("Total population is " + totalPopulation);
	}
	
	//display parking fine per capita to screen
	protected void displayParkingFinePerCapita() {
		TreeMap<String, Double> finePerCapitia = violationProcessor.totalFinesPerCapita(populationMap);
		for(String s: finePerCapitia.keySet()) {
			//format the number with 4 decimal digits
			Double finePerCapitita = finePerCapitia.get(s);
			DecimalFormat df = new DecimalFormat("0.0000");
			System.out.println(s + " " + df.format(finePerCapitita));
		}
	}
	
	//display average residential market value 
	protected void displayAverageResidentialMarketValue() {
		displayAverage("MarketValue");
	}
	
	//display average residential livable area
	protected void displayAverageResidentialTotalLivableArea() {
		displayAverage("LivableArea");
	}
	
	//display total residential livable area per capita
	protected void displayTotalResidentialMarketValuePerCapita() {
		displayAverage("MarketValuePerCapita");
	}
	
	//common methods shared by 3 methods, strategy pattern
	private void displayAverage(String type) {
		Logger logger = Logger.getInstance();
		System.out.println("Enter a ZIP code");
		int average = 0; 
		String zipcode = in.next();
		//if the input is the right format zip code, processing the data and display the results
		if(EChecker.is5DigitZip(zipcode)) {
			logger.log(zipcode);
			if(type.equals("LivableArea")) {
				average = propertyProcessor.averageResidentialTotalLivableArea(zipcode);
			}
			if(type.equals("MarketValuePerCapita")) {
				average = propertyProcessor.totalResidentialMarketValuePerCapita(zipcode, populationMap);
			}
			if(type.equals("MarketValue")) {
				average = propertyProcessor.averageResidentialMarketValue(zipcode);
			}
		}
		System.out.println(average);
	}
	
	//display the total residential livable area per capita in the zipcode location with highest total parking fine
	private void displayTotalResidentialLivableAreaPerCapitaInHighestFineLocation() {
		String highestFineZipcode = violationProcessor.getHighestFineLocation();
		int average = propertyProcessor.totalResidentialLivableAreaPerCapita(highestFineZipcode, populationMap);
		System.out.println("ZIP code with the highest total parking fine is " + highestFineZipcode 
				           + "\nThe total residential livable area per Capita in this location is " + average);
	}

}
