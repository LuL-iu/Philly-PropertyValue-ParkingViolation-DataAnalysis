package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import edu.upenn.cit594.logging.Logger;

/**
 * violation CSV file reader opens parking file and read data to violation Map
 * 
 * @author Kai and Lu
 *
 */

public class ViolationCSVReader implements Reader, ViolationReader {
	protected String fileName;
	private HashMap<String, Double> zipcodesWithParking = new HashMap<String, Double>();
	private ErrorChecker EChecker = new ErrorChecker();

	public ViolationCSVReader(String name) {
		fileName = name;
	}
    
	@Override
	// get data from CSV file
	public HashMap<String, Double> getViolationMap() {
		// log filename
		Logger logger = Logger.getInstance();
		logger.log(fileName);

		// check file permissions and open
		EChecker.checkReadability(fileName);
		File f = new File(fileName);

		Scanner scanner = null; // Get scanner instance

		try {
			scanner = new Scanner(f); // new File(filename));
			// Set the delimiter used in file
			scanner.useDelimiter(",|\\\n");
			while (scanner.hasNext()) {
				scanner.next(); // 0
				String fine = scanner.next();
				scanner.next(); // 2
				scanner.next(); // 3
				String state = scanner.next(); // 4
				scanner.next(); // 5
				String zipcode = scanner.next(); // 6
				zipcode = zipcode.replaceAll("\\s", "");
				zipcode = zipcode.replaceAll("\\-", "");
				if (EChecker.isValidZip(zipcode) && state.contentEquals("PA") && EChecker.isNumber(fine)) {
					Double fineValue = Double.parseDouble(fine);
					fillInMap(zipcode, fineValue);
				}
			}

		} catch (Exception e) {
			System.out.println("Error with csv parking file. Please try again");
			System.exit(-1);
		} finally {
			scanner.close();
		}
		return zipcodesWithParking;

	}

	// this is repeated from json maybe should be placed into Reader?
	protected void fillInMap(String zip_code, double fine) {
		zip_code = zip_code.substring(0, 5);
		if (zipcodesWithParking.containsKey(zip_code)) {
			fine += zipcodesWithParking.get(zip_code);
		}
		zipcodesWithParking.put(zip_code, fine);

	}

//	public static void main(String[] args) {
//		String filename = "parking.csv";
//		// String filename - "practice2.csv";
//		// filename = "practice3_smallZip.csv";
//		// filename = "practice4_smallPropertiesAllGoo.csv";
//		// filename = "practice5_moreZip.csv";
//		ViolationCSVReader read = new ViolationCSVReader(filename);
//		Map<String, Double> l = read.getViolationMap();
//		Iterator it = l.entrySet().iterator();
//		while (it.hasNext()) {
//			Map.Entry pair = (Map.Entry) it.next();
//			System.out.println(pair.getKey() + " = " + pair.getValue().toString());
//			// it.remove(); // avoids a ConcurrentModificationException
//		}
//
//		System.out.println(l.size());
//	}

}
