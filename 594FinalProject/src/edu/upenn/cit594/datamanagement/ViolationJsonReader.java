package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cit594.logging.Logger;

/**
 * violation json file reader opens parking file and read data to violation Map
 * 
 * @author Kai and Lu
 *
 */

public class ViolationJsonReader implements ViolationReader {

	protected String fileName;
	private HashMap<String, Double> zipcodesWithParking = new HashMap<String, Double>();
	private ErrorChecker EChecker = new ErrorChecker();

	public ViolationJsonReader(String name) {
		fileName = name;
	}

	// gets all data from file
	@SuppressWarnings("rawtypes")
	public HashMap<String, Double> getViolationMap() {
		// check file permissions and open
		EChecker.checkReadability(fileName);
		File f = new File(fileName);
		
		// log filename
		Logger logger = Logger.getInstance();
		logger.log(fileName);
		
		try {
			// create a parser
			JSONParser parser = new JSONParser();
			// open the file and get the array of JSON objects
			JSONArray violations = (JSONArray) parser.parse(new FileReader(fileName));
			// use an iterator to iterate over each element of the array
			Iterator iter = violations.iterator();
			// iterate while there are more objects in array
			while (iter.hasNext()) {

				// get the next JSON object
				JSONObject violation = (JSONObject) iter.next();
				// use the "get" method to print the value associated with that key
				if (violation.get("state").toString().equals("PA")) {
					String zipcode = violation.get("zip_code").toString();
					Object fineObj = violation.get("fine");
					String fine = fineObj.toString();
					zipcode = zipcode.replaceAll("\\s", "");
					zipcode = zipcode.replaceAll("\\-", "");
					if (EChecker.isValidZip(zipcode) && EChecker.isNumber(fine)) {
						Double fineValue = Double.parseDouble(fine);
						fillInMap(zipcode, fineValue);
					}
				}
			}

		} catch (Exception e) {
			System.out.println("ERROR: with json file. Please try again");

		}
		return zipcodesWithParking;
	}

	protected void fillInMap(String zip_code, double fine) {
		zip_code = zip_code.substring(0, 5);
		if (zipcodesWithParking.containsKey(zip_code)) {
			fine += zipcodesWithParking.get(zip_code);
		}
		zipcodesWithParking.put(zip_code, fine);
	}


}
