package edu.upenn.cit594.ui;

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
 *
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
		System.out.println("Enter 0 to Exit\nEnter 1 to show total population for all ZIP codes\n"
				+ "Enter 2 to show total parking fines per capita for each ZIP codes\n"
				+ "Enter 3 to show average market value for residents in specified ZIP code\n"
				+ "Enter 4 to show average total livable area for residents in specified ZIP code\n"
				+ "Enter 5 to show total residential market value per capita for specifed ZIP code\n"
				+ "Enter 6 to show the total residential livable area Per Capita in zip code with highest total parking fine ");
		populationProcessor.buildMap();
		violationProcessor.buildMap();
		propertyProcessor.buildMap();
		populationMap = populationProcessor.getPopulationMap();
		while(true) {
//			int choice = in.nextInt();
//			Logger logger = Logger.getInstance();
//			logger.log(Integer.toString(choice));
//			if(choice == 0) {
//				break;
//			}
//			
//			else if(choice == 1) {
//				displayTotalPopulation();
//			}
//			
//			else if(choice == 2) {
//				displayParkingFinePerCapita();
//			}
//			
//			else if(choice == 3) {
//				displayAverageResidentialMarketValue();
//			}
//			
//			else if(choice == 4) {
//				displayAverageResidentialTotalLivableArea();
//			}
//			
//			else if(choice == 5) {
//				displayTotalResidentialMarketValuePerCapita();
//			}
//			
//			else if(choice == 6) {
//				displayTotalResidentialLivableAreaPerCapitaInHighestFineLocation();
//			}
//			
//			else {
//				System.out.println("Error Input, program will exit");
//				//break;
//				System.exit(0);
//			}
//		}
//		in.close();
			
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
	
	protected void displayTotalPopulation() { 
		int totalPopulation = populationProcessor.totalPopulation();
		System.out.println("Total population is " + totalPopulation);
	}
	
	protected void displayParkingFinePerCapita() {
		TreeMap<String, Double> finePerCapitia = violationProcessor.totalFinesPerCaptia(populationMap);
		for(String s: finePerCapitia.keySet()) {
			System.out.println(s + " " + finePerCapitia.get(s));
		}
	}
	
	protected void displayAverageResidentialMarketValue() {
		displayAverage("MarketValue");
	}
	
	protected void displayAverageResidentialTotalLivableArea() {
		displayAverage("LivableArea");
	}
	
	protected void displayTotalResidentialMarketValuePerCapita() {
		displayAverage("MarketValuePerCapita");
	}
	
	private void displayAverage(String type) {
		Logger logger = Logger.getInstance();
		System.out.println("Enter a ZIP code");
		int average = 0; 
		String zipcode = in.next();
		zipcode.replaceAll("\\s",  "");
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
	
	private void displayTotalResidentialLivableAreaPerCapitaInHighestFineLocation() {
		String highestFineZipcode = violationProcessor.getHighestFineLocation();
		int average = propertyProcessor.totalResidentialLivableAreaPerCapita(highestFineZipcode, populationMap);
		System.out.println("Zipcode with highest total parking fine is " + highestFineZipcode 
				           + "\nThe total residential livable area per Capita in this location is " + average);
	}

}
