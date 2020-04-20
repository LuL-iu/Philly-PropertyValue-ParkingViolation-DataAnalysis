package edu.upenn.cit594.datamanagement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PopulationReader implements CSVReader, Reader{
	public HashMap<Integer, Integer> populationMap;
	
	
	public HashMap<Integer, Integer> getPopulationMap(){
		return populationMap;
		
	}
	
	
	protected String filename;

	public PopulationReader(String name) {
		filename = name;
	}

	// gets all tweet data from text file
	@SuppressWarnings("resource")
	public Map<Integer, Integer> getDataFromFile() {
		Map<Integer, Integer> zipCodePopulations = new HashMap<Integer, Integer>();

		// check file permissions and open
		File f = ErrorChecker.checkReadability(filename);

		try {
			// File file = new File(filename); // Creation of File Descriptor for input file
			FileReader fileReader = new FileReader(f); // Creation of File Reader object

			BufferedReader br = new BufferedReader(fileReader); // Creation of BufferedReader object

			String line;
			while ((line = br.readLine()) != null) {
				String lineSplit[] = line.split(" ");
				zipCodePopulations.put(Integer.parseInt(lineSplit[0]), Integer.parseInt(lineSplit[1]));

			}
		} catch (Exception e) {

			System.out.println("ERROR in textFileReader");
			System.exit(0);
		}
		return zipCodePopulations;

	}
}
