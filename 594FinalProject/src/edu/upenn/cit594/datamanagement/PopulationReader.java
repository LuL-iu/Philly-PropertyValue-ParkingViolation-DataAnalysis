package edu.upenn.cit594.datamanagement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PopulationReader implements Reader{
	protected String filename;
	private Map<String, Integer> zipCodePopulations;
	private ErrorChecker EChecker = new ErrorChecker();

	public PopulationReader(String name) {
		filename = name;
	}

	// gets all data from text file
	public Map<String, Integer> getDataFromFile() {
		zipCodePopulations = new HashMap<String, Integer>();

		// check file permissions and open

		
			// File file = new File(filename); // Creation of File Descriptor for input file
			File f = new File(filename);
			FileReader fileReader;
			try {
				fileReader = new FileReader(f);
				BufferedReader br = new BufferedReader(fileReader); // Creation of BufferedReader object

				String line;
				try {
					while ((line = br.readLine()) != null) {
						String lineSplit[] = line.split(" ");
						String zipcode = lineSplit[0];
						String population = lineSplit[1];
						zipcode = zipcode.replaceAll("\\s", "");
						if(EChecker.isValidZip(zipcode) && EChecker.isNumber(population)) {
							int pNumber = Integer.parseInt(population); 
							if(zipCodePopulations.containsKey(zipcode)) {
								int n = zipCodePopulations.get(zipcode);
								pNumber += n;
							}
							zipCodePopulations.put(zipcode, pNumber);
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
