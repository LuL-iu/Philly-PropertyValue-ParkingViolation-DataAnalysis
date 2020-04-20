package edu.upenn.cit594.datamanagement;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("rawtypes")
public class ViolationCSVReader implements Reader, ViolationReader {
	protected String filename;

	public ViolationCSVReader(String name) {
		filename = name;
	}

	// get data from CSV file
	public Map<Integer, Integer> getViolationMap() {
		Map<Integer, Integer> zipcodes = new HashMap<Integer, Integer>();
		Scanner scanner = null; // Get scanner instance

		// check file permissions and open
		File f = ErrorChecker.checkReadability(filename);

		try {

			scanner = new Scanner(f); // new File(filename));

			// Set the delimiter used in file
			scanner.useDelimiter(",|\\\n");

			while (scanner.hasNext()) {

				scanner.next(); // 0
				scanner.next(); // 1
				scanner.next(); // 2
				scanner.next(); // 3
				String state = scanner.next(); // 4
				// System.out.println(state);
				scanner.next(); // 5
				String zipcode = scanner.next(); // 6
				if (zipcode.length() == 5 && state.contentEquals("PA")) {
					fillInMap(zipcodes, zipcode);
				}
			}

		} catch (Exception e) {
			// throw new IllegalStateException(e);
		} finally {
			scanner.close();
		}
		return zipcodes;

	}

	// this is repeated from json maybe should be placed into Reader?
	protected void fillInMap(Map<Integer, Integer> zipcodesWithParking, String zip_code) {
		if (zip_code.length() == 5) {
			if (!zipcodesWithParking.containsKey(Integer.parseInt(zip_code))) {
				zipcodesWithParking.put(Integer.parseInt(zip_code), 1);
			} else {
				int tempZip = zipcodesWithParking.get(Integer.parseInt(zip_code)) + 1;
				zipcodesWithParking.put(Integer.parseInt(zip_code), tempZip);
			}
		}
	}

	public static void main(String[] args) {
		String filename = "parking.csv";
		// String filename - "practice2.csv";
		// filename = "practice3_smallZip.csv";
		// filename = "practice4_smallPropertiesAllGoo.csv";
		// filename = "practice5_moreZip.csv";
		ViolationCSVReader read = new ViolationCSVReader(filename);
		Map<Integer, Integer> l = read.getViolationMap();
		Iterator it = l.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue().toString());
			// it.remove(); // avoids a ConcurrentModificationException
		}

		System.out.println(l.size());
	}
	
}
