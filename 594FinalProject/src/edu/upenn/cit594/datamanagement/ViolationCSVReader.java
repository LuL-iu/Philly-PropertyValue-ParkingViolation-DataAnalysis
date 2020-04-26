package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.logging.Logger;

@SuppressWarnings("rawtypes")
public class ViolationCSVReader implements Reader, ViolationReader {
	protected String filename;
	private TreeMap<String, Double> zipcodesWithParking = new TreeMap();
	private ErrorChecker EChecker = new ErrorChecker();

	public ViolationCSVReader(String name) {
		filename = name;
	}

	// get data from CSV file
	public TreeMap<String, Double> getViolationMap() {
		// log filename
		Logger logger = Logger.getInstance();
		logger.log(filename);

		Scanner scanner = null; // Get scanner instance

		// check file permissions and open
		EChecker.checkReadability(filename);
		File f = new File(filename);

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
				// System.out.println(state);
				scanner.next(); // 5
				String zipcode = scanner.next(); // 6
				zipcode = zipcode.replaceAll("\\s", "");
				if (EChecker.is5DigitZip(zipcode) && state.contentEquals("PA") && EChecker.isNumber(fine)) {
					Double fineValue = Double.parseDouble(fine);
					fillInMap(zipcode, fineValue);
				}
			}

		} catch (Exception e) {
			System.out.println("Error with csv parking file. Please try again");
			System.exit(1);
		} finally {
			scanner.close();
		}
		return zipcodesWithParking;

	}

	// this is repeated from json maybe should be placed into Reader?
	protected void fillInMap(String zip_code, double fine) {
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
