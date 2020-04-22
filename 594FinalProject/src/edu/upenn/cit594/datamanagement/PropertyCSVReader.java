//TO DO
//need fix processor
// need to checkreadabiity is working maybe filereader instead


package edu.upenn.cit594.datamanagement;
//TO DO
//living area is a double
// if any null than discard
//change zipcode to int
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import edu.upenn.cit594.data.PropertyValues;

/**
 * CSV file reader opens CSV states
 * 
 * @author Kai and Lu
 *
 */
@SuppressWarnings("rawtypes")

public class PropertyCSVReader implements Reader {
	protected String filename;
	private int total_livable_area, market_value, zip_code, countRow = 0;
	private ErrorChecker ECheck = new ErrorChecker();
	private HashMap<String, List<PropertyValues>> propertyMap;

	public PropertyCSVReader(String name) {
		filename = name;
		propertyMap = new HashMap();
	}

	// get data from CSV file
	@SuppressWarnings("resource")
	public HashMap<String, List<PropertyValues>> getPropertyMap() {
		//countRow = 0; // this is for error checking
		String line = null;														
		// Scanner scanner = null; // Get scanner instance

		// check file permissions and open
		// File f = checkReadability(filename);
		try {
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(filename));
			//String line = null;
			if ((line = reader.readLine()) != null) {
				//System.out.println(line);
				setUpHeaderVariablesResCSV(line);
				countRow++;
			}
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				seperateDataForResCSV(line);
				countRow++;
			}
		} catch (Exception e) {
			// TODO Auto- generated catch block
			System.out.println("ERROR CSVReader at ROW " + countRow + "  the following line:\n" + line);
			System.exit(0);
		}

		return propertyMap;
	}

	protected void setUpHeaderVariablesResCSV(String csvLine) {

		String[] header = csvLine.split(",");

		for (int k = 0; k < header.length; k++) {
			if (header[k].equals("total_livable_area")) {
				total_livable_area = k;
				//System.out.println("livable head = " + k);
			}
			if (header[k].equals("market_value")) {
				market_value = k;
				//System.out.println("market_value = " + k);
			}
			if (header[k].equals("zip_code")) {
				zip_code = k;
				//System.out.println("zip_code = " + k);
			}
		}
	}

	// we can also use REGEX:
	// https://stackoverflow.com/questions/11456850/split-a-string-by-commas-but-ignore-commas-within-double-quotes-using-javascript
	// or possibly "/'[^']+'|[^,]+/"
	protected void seperateDataForResCSV(String csvLine) {
		String[] cells = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		//System.out.println(cells.length);
		String livableArea = " ";
		String marketValue = " ";
		String zipcode = " ";
		for (int i = 0; i < cells.length; i ++) {
			if (i == total_livable_area) {
				livableArea = cells[i];
			}
			if(i == zip_code) {
				zipcode = cells[i];
				zipcode = zipcode.replaceAll("\\s", "");
				zipcode = zipcode.replaceAll("\\-", "");
				
				
			}
			if(i == market_value) {
				marketValue = cells[i];
			}
		}
		
		if(ECheck.isValidZip(zipcode) && ECheck.isNumber(livableArea) && ECheck.isNumber(marketValue)) {
			zipcode = zipcode.substring(0, 5);
			double lArea = Double.parseDouble(livableArea);
			double mValue = Double.parseDouble(marketValue);
			PropertyValues pValue = new PropertyValues(mValue, lArea);
			List<PropertyValues> l = new ArrayList();
			if(propertyMap.containsKey(zipcode)) {
				l = propertyMap.get(zipcode);
				
			}
			l.add(pValue);
			propertyMap.put(zipcode, l);
		}
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

