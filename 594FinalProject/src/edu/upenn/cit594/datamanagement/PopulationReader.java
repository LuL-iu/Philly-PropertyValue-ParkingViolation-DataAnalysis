package edu.upenn.cit594.datamanagement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PopulationReader implements Reader{
	protected String filename;

	public PopulationReader(String name) {
		filename = name;
	}

	// gets all data from text file
	@SuppressWarnings("resource")
	public Map<Integer, Integer> getDataFromFile() {
		Map<Integer, Integer> zipCodePopulations = new HashMap<Integer, Integer>();

		// check file permissions and open

		try {
			// File file = new File(filename); // Creation of File Descriptor for input file
			FileReader fileReader = new FileReader(new File(filename)); // Creation of File Reader object

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "population.txt";
		PopulationReader pr = new PopulationReader(fileName);
		HashMap<Integer, Integer> map = (HashMap<Integer, Integer>) pr.getDataFromFile();
		for(int i : map.keySet()) {
			System.out.println("zip : " + i + "\n" + "population " + map.get(i));
			
		}
	}
}
