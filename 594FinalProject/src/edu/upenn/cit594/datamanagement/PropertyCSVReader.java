
package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.logging.Logger;

/**
 * CSV file reader opens CSV properties and read data to Property Map
 * 
 * @author Kai and Lu
 *
 */

public class PropertyCSVReader implements Reader {

	protected String fileName;
	private int total_livable_area, market_value, zip_code, countRow = 0;
	private ErrorChecker EChecker = new ErrorChecker();
	private HashMap<String, List<PropertyValues>> propertyMap;

	public PropertyCSVReader(String name) {
		fileName = name;
		propertyMap = new HashMap<String, List<PropertyValues>>();
	}

	// get data from CSV file
	@SuppressWarnings("resource")
	public HashMap<String, List<PropertyValues>> getPropertyMap() {
		Logger logger = Logger.getInstance();
		logger.log(fileName);

		// check file permissions and open
		EChecker.checkReadability(fileName);
		// log filename

		String line = null;
		try {
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(fileName));
			if ((line = reader.readLine()) != null) {
				setUpHeaderVariablesResCSV(line);
				countRow++;
			}
			while ((line = reader.readLine()) != null) {
				seperateDataForResCSV(line);
				countRow++;
			}
		} catch (Exception e) {
			// TODO Auto- generated catch block
			System.out.println("ERROR CSVReader at ROW " + countRow + "  the following line:\n" + line);
			System.exit(-1);
		}

		return propertyMap;
	}

	// get the header column number of total livable area, market value and zip code
	protected void setUpHeaderVariablesResCSV(String csvLine) {
		String[] header = csvLine.split(",");

		for (int k = 0; k < header.length; k++) {
			if (header[k].equals("total_livable_area")) {
				total_livable_area = k;
			}
			if (header[k].equals("market_value")) {
				market_value = k;
			}
			if (header[k].equals("zip_code")) {
				zip_code = k;
			}
		}
	}

	protected void seperateDataForResCSV(String csvLine) {
		// replace the comma in quotes with ";" then use comma to split the string 
		String newCSVLine = replaceCommaInQuotes(csvLine);
		String[] cells = newCSVLine.split(",");
//		String[] cells = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		String livableArea = " ";
		String marketValue = " ";
		String zipcode = " ";
		// get data accordingly
		for (int i = 0; i < cells.length; i++) {
			if (i == total_livable_area) {
				livableArea = cells[i];
			}
			if (i == zip_code) {
				zipcode = cells[i];
				zipcode = zipcode.replaceAll("\\s", "");
				zipcode = zipcode.replaceAll("\\-", "");
			}
			if (i == market_value) {
				marketValue = cells[i];
			}
		}

		if (EChecker.isValidZip(zipcode) && EChecker.isNumber(livableArea) && EChecker.isNumber(marketValue)) {
			zipcode = zipcode.substring(0, 5);
			double lArea = Double.parseDouble(livableArea);
			double mValue = Double.parseDouble(marketValue);
			PropertyValues pValue = new PropertyValues(mValue, lArea);
			List<PropertyValues> l = new ArrayList<PropertyValues>();
			if (propertyMap.containsKey(zipcode)) {
				l = propertyMap.get(zipcode);
			}
			l.add(pValue);
			propertyMap.put(zipcode, l);
		}
	}
	
	//replace the comma in quotes with ";"
	public String replaceCommaInQuotes(String s) {
		for(int i = 0; i < s.length(); i ++) {
			if(s.charAt(i) == '"') {
				for(int a = i + 1; a < s.length(); a ++) {	
					if(s.charAt(a) == '"') {
						String subString = s.substring(i,a);
						subString = subString.replaceAll(",", ";");
						s = s.substring(0,i) + subString + s.substring(a);
						i = a;
						break;
					}
				}
			}
		}
		return s;
	}

//	public static void main(String[] args) {
//		String filename = "properties.csv";
//		PropertyCSVReader read = new PropertyCSVReader(filename);
//		Map<String, List<PropertyValues>> l = read.getPropertyMap();
//		for(String s : l.keySet()) {
//			System.out.println( "zip: " + s +" " + l.get(s).size());
//			for(PropertyValues pv : l.get(s)) {
//				System.out.println("ma: " + pv.getMaketValue());
//				System.out.println("la" + pv.getTotalLivableArea());
//			}
//		}
//	}
}
