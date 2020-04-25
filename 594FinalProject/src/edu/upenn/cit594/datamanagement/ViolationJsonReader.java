package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cit594.logging.Logger;

public class ViolationJsonReader implements ViolationReader {

	protected String filename;
	private TreeMap<String, Double> zipcodesWithParking = new TreeMap();
	private ErrorChecker EChecker = new ErrorChecker();

	public ViolationJsonReader(String name) {
		filename = name;
	}

	// gets all data from file
	@SuppressWarnings("rawtypes")
	public Map getViolationMap() {
		// log filename
		Logger logger = Logger.getInstance();
		logger.log(filename);
		// Map<String, Double> zipcodesWithParking = new TreeMap<String, Double>();

		// check file permissions and open
		EChecker.checkReadability(filename);
		File f = new File(filename);
		try {
			// create a parser
			JSONParser parser = new JSONParser();
			// open the file and get the array of JSON objects
			JSONArray violations = (JSONArray) parser.parse(new FileReader(filename));
			// use an iterator to iterate over each element of the array
			Iterator iter = violations.iterator();
			// iterate while there are more objects in array
			while (iter.hasNext()) {

				// get the next JSON object
				JSONObject violation = (JSONObject) iter.next();
				// use the "get" method to print the value associated with that key

				if (violation.get("state").toString().equals("PA")) {
					String zipcode = violation.get("zip_code").toString();
//				System.out.println(violation.get("state").toString());
//				System.out.println(zipcode);
					zipcode = zipcode.replaceAll("\\s", "");
					Object fineObj = violation.get("fine");
					String fine = fineObj.toString();
					if (EChecker.is5DigitZip(zipcode) && EChecker.isNumber(fine)) {
						// System.out.println(fine);
						Double fineValue = Double.parseDouble(fine);
						fillInMap(zipcode, fineValue);
					}
				}
			}

		} catch (Exception e) {
			System.out.println("ERROR: jsonReader");

		}
//		System.out.println("THE COUNT IS " + count);
		return zipcodesWithParking;

	}

	protected void fillInMap(String zip_code, double fine) {
		if (zipcodesWithParking.containsKey(zip_code)) {
			fine += zipcodesWithParking.get(zip_code);
		}
		zipcodesWithParking.put(zip_code, fine);
	}

//	public static void main(String[] args) {
//		String filename = "parking.json";
//		ViolationJsonReader tr = new ViolationJsonReader(filename);
//		Map <Integer, Integer> m = tr.getViolationMap();
//		@SuppressWarnings("rawtypes")
//		Iterator it = m.entrySet().iterator();
//		while (it.hasNext()) {
//			Map.Entry pair = (Map.Entry) it.next();
//		System.out.println(pair.getKey() + " = " + pair.getValue().toString());
//		//   it.remove(); // avoids a ConcurrentModificationException
//		   
//	}
//		System.out.println(m.size());
//
//	
//	}

}
