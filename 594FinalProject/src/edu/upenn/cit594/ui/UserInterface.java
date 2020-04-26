package edu.upenn.cit594.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.ErrorChecker;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;
import edu.upenn.cit594.processor.ViolationProcessor;

/**
 * this is user interface class, it is responsible for the interacting with user
 * and processing data as it relates to display it.
 * 
 * @author Lu & Kai
 */

public class UserInterface {
	protected PopulationProcessor populationProcessor;
	protected ViolationProcessor violationProcessor;
	protected PropertyProcessor propertyProcessor;
	protected Scanner in;
	protected ErrorChecker EChecker = new ErrorChecker();
	protected Map<String, Integer> populationMap;

	// memoization
	int populationResults = -1; // -1 = unused by userInput
	TreeMap<String, Double> finePerCapitaResults = new TreeMap<String, Double>();
	Map<String, Integer> marketValueResults = new HashMap<String, Integer>();
	Map<String, Integer> totalLivableAreaResults = new HashMap<String, Integer>();
	Map<String, Integer> MarketValuePerCapitaResults = new HashMap<String, Integer>();
	ArrayList<Integer> additionalFeatureResult = new ArrayList<Integer>();

	public UserInterface(PopulationProcessor populationProcessor, ViolationProcessor violationProcessor,
			PropertyProcessor propertyProcessor) {
		this.populationProcessor = populationProcessor;
		this.violationProcessor = violationProcessor;
		this.propertyProcessor = propertyProcessor;
		in = new Scanner(System.in);
	}

	public void start() {
		// display options to screen;
		System.out.println("Enter 0 to Exit\nEnter 1 to show total population for all ZIP codes\n"
				+ "Enter 2 to show total parking fines per capita for each ZIP codes\n"
				+ "Enter 3 to show average market value for residents in specified ZIP code\n"
				+ "Enter 4 to show average total livable area for residents in specified ZIP code\n"
				+ "Enter 5 to show total residential market value per capita for specifed ZIP code\n"
				+ "Enter 6 to show the total residential livable area Per Capita in zip code with highest total parking fine ");

		// set up data map in processor(start to read data from the 3 files)
		populationProcessor.buildMap();
		violationProcessor.buildMap();
		propertyProcessor.buildMap();
		populationMap = populationProcessor.getPopulationMap();

		// run different methods based on user input
		while (true) {
			String choice = in.next();
			Logger logger = Logger.getInstance();
			logger.log(choice);
			if (choice.equals("0")) {
				break;
			}

			else if (choice.equals("1")) {
				displayTotalPopulation();
			}

			else if (choice.equals("2")) {
				displayParkingFinePerCapita();
			}

			else if (choice.equals("3")) {
				displayAverageResidentialMarketValue();
			}

			else if (choice.equals("4")) {
				displayAverageResidentialTotalLivableArea();
			}

			else if (choice.equals("5")) {
				displayTotalResidentialMarketValuePerCapita();
			}

			else if (choice.equals("6")) {
				displayTotalResidentialLivableAreaPerCapitaInHighestFineLocation();
			}

			else {
				System.out.println("Error Input, program will exit");
				// break;
				System.exit(0);
			}
		}
		in.close();
	}

	// display total population to screen
	protected void displayTotalPopulation() {
		if (populationResults == -1) {
			int totalPopulation = populationProcessor.totalPopulation();
			System.out.println("Total population is " + totalPopulation);
			populationResults = totalPopulation;
		} else {
			System.out.println("Total population is " + populationResults);
		}
	}

	// display parking fine per capita to screen
	protected void displayParkingFinePerCapita() {
		if (finePerCapitaResults.isEmpty()) {
			finePerCapitaResults = violationProcessor.totalFinesPerCapita(populationMap); // memoization
		}
		for (String s : finePerCapitaResults.keySet()) {
			// format the number with 4 decimal digits
			Double finePerCapitita = finePerCapitaResults.get(s);
			DecimalFormat df = new DecimalFormat("0.0000");
			System.out.println(s + " " + df.format(finePerCapitita));
		}
	}

	// display average residential market value
	protected void displayAverageResidentialMarketValue() {
		displayAverage("MarketValue");
	}

	// display average residential livable area
	protected void displayAverageResidentialTotalLivableArea() {
		displayAverage("LivableArea");
	}

	// display total residential livable area per capita
	protected void displayTotalResidentialMarketValuePerCapita() {
		displayAverage("MarketValuePerCapita");
	}

	// common methods shared by 3 methods, strategy pattern
	private void displayAverage(String type) {
		Logger logger = Logger.getInstance();
		System.out.println("Enter a ZIP code");
		int average = 0;
		String zipcode = in.next();
		logger.log(zipcode); // we are suppose to log even incorrect zip codes!
		// if the input is the right format zip code, processing the data and display
		// the results
		if (EChecker.is5DigitZip(zipcode)) {
			if (type.equals("LivableArea")) {
				if (!totalLivableAreaResults.containsKey(zipcode)) {
					average = propertyProcessor.averageResidentialTotalLivableArea(zipcode);
					totalLivableAreaResults.put(zipcode, average); // memoization
				} else {
					average = totalLivableAreaResults.get(zipcode);
				}
			}
			if (type.equals("MarketValuePerCapita")) {
				if (!MarketValuePerCapitaResults.containsKey(zipcode)) {
					average = propertyProcessor.totalResidentialMarketValuePerCapita(zipcode, populationMap);
					MarketValuePerCapitaResults.put(zipcode, average); // memoization
				} else {
					average = MarketValuePerCapitaResults.get(zipcode);
				}
			}
			if (type.equals("MarketValue")) {
				if (!marketValueResults.containsKey(zipcode)) {
					average = propertyProcessor.averageResidentialMarketValue(zipcode);
					marketValueResults.put(zipcode, average); // memoization
				} else {
					average = marketValueResults.get(zipcode);
				}
			}
		}

		System.out.println(average);
	}

	// display the total residential livable area per capita in the zipcode location
	// with highest total parking fine
	private void displayTotalResidentialLivableAreaPerCapitaInHighestFineLocation() {
		if (additionalFeatureResult.isEmpty()) {
			String highestFineZipcode = violationProcessor.getHighestFineLocation();
			int average = propertyProcessor.totalResidentialLivableAreaPerCapita(highestFineZipcode, populationMap);
			System.out.println("ZIP code with the highest total parking fine is " + highestFineZipcode
					+ "\nThe total residential livable area per Capita in this location is " + average);
			additionalFeatureResult.add(Integer.parseInt(highestFineZipcode));
			additionalFeatureResult.add(average);
		} else {
			System.out.println("ZIP code with the highest total parking fine is " + additionalFeatureResult.get(0)
					+ "\nThe total residential livable area per Capita in this location is "
					+ additionalFeatureResult.get(1));
		}
	}

}
