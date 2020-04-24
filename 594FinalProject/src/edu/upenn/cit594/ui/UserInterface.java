package edu.upenn.cit594.ui;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.ErrorChecker;
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
	
	public UserInterface(PopulationProcessor populationProcessor, ViolationProcessor violationProcessor, PropertyProcessor propertyProcessor) {
		this.populationProcessor = populationProcessor;
		this.violationProcessor = violationProcessor;
		this.propertyProcessor = propertyProcessor;
		in = new Scanner(System.in);
	}
	
	public void start() {
		System.out.println("Enter 0 to Exit\nEnter 1 to show total population for all ZIP codes\nEnter 2 to show total parking fines per capita for"
				+ " each ZIP codes\nEnter 3 to show average market value for residents in specified ZIP code\nEnter 4 to1 show average total livavle area"
				+ "for residents in specified ZIP code\nEnter 5 to show total residential market value per capita for specifed ZIP code\nEnter "
				+ "6 to show the results of your custom feature");
		populationProcessor.buildMap();
		violationProcessor.buildMap();
		propertyProcessor.buildMap();
		while(true) {
			int choice = in.nextInt();
			if(choice == 0) {
				break;
			}
			
			if(choice == 1) {
				displayTotalPopulation();
			}
			
			if(choice == 2) {
				displayParkingFinePerCapita();
			}
			
			if(choice == 3) {
				displayAverageResidentialMarketValue();
			}
			
			if(choice == 4) {
				displayAverageResidentialTotalLivableArea();
			}
			
			if(choice == 5) {
				displayTotalResidentialMarketValuePerCapita();
			}
			
			if(choice == 6) {
				displayTotalResidentialMarketValuePerCapitaInHighestFineLocation();
			}
		}
		in.close();
	}
	
	protected void displayTotalPopulation() { 
		int totalPopulation = populationProcessor.totalPopulation();
		System.out.println("Total population is " + totalPopulation);
	}
	
	protected void displayParkingFinePerCapita() {
		Map<String, Integer> populationMap = populationProcessor.getPopulationMap();
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
		System.out.println("Enter a ZIP code");
		int average = 0; 
		String zipcode = in.next();
		zipcode.replaceAll("\\s",  "");
		if(EChecker.is5DigitZip(zipcode)) {
			if(type.equals("LivableArea")) {
				average = propertyProcessor.averageResidentialTotalLivableArea(zipcode);
			}
			if(type.equals("MarketValuePerCapita")) {
				Map<String, Integer> populationMap = populationProcessor.getPopulationMap();
				average = propertyProcessor.totalResidentialMarketValuePerCapita(zipcode, populationMap);
			}
			if(type.equals("MarketValue")) {
				average = propertyProcessor.averageResidentialMarketValue(zipcode);
			}
		}
		System.out.println(average);
	}
	
	private void displayTotalResidentialMarketValuePerCapitaInHighestFineLocation() {
		String highestFineZipcode = violationProcessor.getHighestFineLocation();
		Map<String, Integer> populationMap = populationProcessor.getPopulationMap();
		int average = propertyProcessor.totalResidentialLivableAreaPerCapita(highestFineZipcode, populationMap);
		System.out.println(average);
	}

}
