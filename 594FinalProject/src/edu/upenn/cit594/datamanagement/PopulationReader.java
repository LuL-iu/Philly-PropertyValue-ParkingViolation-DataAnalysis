package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.upenn.cit594.logging.Logger;

/**
 * this is reader class, read the population file and add the population to
 * population map
 * 
 * @author Kai & Lu
 * 
 */
public class PopulationReader implements Reader {
	protected String fileName;
	private Map<String, Integer> zipCodePopulations;
	private ErrorChecker EChecker = new ErrorChecker();

	public PopulationReader(String name) {
		fileName = name;
	}

	// create population map, key is zip code
	public Map<String, Integer> getDataFromFile() {
		zipCodePopulations = new HashMap<String, Integer>();
		Logger logger = Logger.getInstance();
		logger.log(fileName);
		// check file permissions and open
		EChecker.checkReadability(fileName);
		// File file = new File(filename); // Creation of File Descriptor for input file
		File f = new File(fileName);
		// log filename

		try {
			FileReader fileReader = new FileReader(f);
			BufferedReader br = new BufferedReader(fileReader); // Creation of BufferedReader object
			String line;
			try {
				while ((line = br.readLine()) != null) {
					String lineSplit[] = line.split(" ");
					String zipcode = lineSplit[0];
					String population = lineSplit[1];
					zipcode = zipcode.replaceAll("\\s", "");
					// check whether zip code is valid and population is a integer
					if (EChecker.isValidZip(zipcode) && EChecker.isInteger(population)) {
						int pNumber = Integer.parseInt(population);
						if (zipCodePopulations.containsKey(zipcode)) {
							int n = zipCodePopulations.get(zipcode);
							pNumber += n;
						}
						zipCodePopulations.put(zipcode, pNumber);
					}
				}
			} catch (IOException e) {
				System.out.println("Error with populations file. Please try again");
				System.exit(-1);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error with populations file. Please try again");
			System.exit(-1);
		} // Creation of File Reader object
		return zipCodePopulations;
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String fileName = "population.txt";
//		PopulationReader pr = new PopulationReader(fileName);
//		HashMap<String, Integer> map = (HashMap<String, Integer>) pr.getDataFromFile();
//		int total = 0;
//		for(String s : map.keySet()) {
//			total += map.get(s);
//		}
//		System.out.println("total is " + total);
//	}
}
