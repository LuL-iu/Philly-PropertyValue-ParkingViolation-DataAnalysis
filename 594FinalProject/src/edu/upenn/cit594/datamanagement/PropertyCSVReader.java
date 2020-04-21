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
import java.util.HashMap;
import java.util.Iterator;
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
	private StringBuilder totLivAreaSB = new StringBuilder();
	private StringBuilder marketValSB = new StringBuilder();
	//private StringBuilder buildCodeSB = new StringBuilder();
	private StringBuilder zipSB = new StringBuilder();

	public PropertyCSVReader(String name) {
		filename = name;
	}

	// get data from CSV file
	@SuppressWarnings("resource")
	public Map<String, PropertyValues> getPropertyMap() {
		//countRow = 0; // this is for error checking
		Map<String, PropertyValues> zipcodes = new HashMap<String, PropertyValues>(); // chosen since we have to add specifically to
		String line = null;															// already used zipcodes
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
				zipcodes = seperateDataForResCSV(line, zipcodes);
				countRow++;
			}
		} catch (Exception e) {
			// TODO Auto- generated catch block
			System.out.println("ERROR CSVReader at ROW " + countRow + "  the following line:\n" + line);
			System.exit(0);
		}

		return zipcodes;
	}

	protected void setUpHeaderVariablesResCSV(String csvLine) {

		String[] header = csvLine.split(",");

		for (int k = 0; k < header.length; k++) {
			if (header[k].equals("total_livable_area")) {
				total_livable_area = k;
			}
			if (header[k].equals("market_value")) {
				market_value = k;
			}
//			if (header[k].equals("building_code")) {
//				building_code = k;
//			}
			if (header[k].equals("zip_code")) {
				zip_code = k;
			}
		}
		//System.out.println(header.toString());
	}

	// we can also use REGEX:
	// https://stackoverflow.com/questions/11456850/split-a-string-by-commas-but-ignore-commas-within-double-quotes-using-javascript
	// or possibly "/'[^']+'|[^,]+/"
	protected Map<String, PropertyValues> seperateDataForResCSV(String csvLine, Map<String, PropertyValues> zipcodes) {
		int columnCount = 0;

		// switches to track writing
		boolean openQuote = false; // off
		// int commaWriteSwitch = 0; // off

		for (int l = 0; l < csvLine.length(); l++) {
			char c = csvLine.charAt(l);
			
			//checks commas inside of cells
			if (c == '"' && openQuote == false)
				openQuote = true;
			else if (c == '"' && openQuote == true)
				openQuote = false;
			
			
			if (columnCount == total_livable_area || columnCount == market_value //|| columnCount == building_code
					|| columnCount == zip_code) {

				

				// normal no quote
				if (openQuote == false && c != ',' && c != '"') {
					addToStringBuilder(columnCount, c);
				}

				// with quote
				if (openQuote == true && c == ',' && c != '"') {
					addToStringBuilder(columnCount, c);
				}

			}
			if (openQuote == false && c == ',') {

				columnCount++;
			}

		}
		
		
		if(ErrorChecker.isValidZip(zipSB.toString())
				&& ErrorChecker.isNumber(marketValSB.toString()) && ErrorChecker.isNumber(totLivAreaSB.toString())) {
			
		if (!zipcodes.containsKey(zipSB.toString().substring(0,5)) ) {
			
			PropertyValues tempPropVal = new PropertyValues(Double.parseDouble(marketValSB.toString()), Double.parseDouble(totLivAreaSB.toString()));
//			if(tempPropVal.updateDataRes(totLivAreaSB.toString(), marketValSB.toString(), buildCodeSB.toString(),
//					zipSB.toString(), countRow)) {
			zipcodes.put(zipSB.toString().substring(0, 5), tempPropVal);
			}
			
		else {//if(ErrorChecker.isValidZip(zipSB.toString()))
			
			//if null or poor quality will not update
			zipcodes.get(zipSB.toString().substring(0,5)).update(Double.parseDouble(marketValSB.toString()), Double.parseDouble(totLivAreaSB.toString()));
			
			//updateDataRes(totLivAreaSB.toString(), marketValSB.toString(),
				//	buildCodeSB.toString(), zipSB.toString(), countRow);
		}
		}
		// reset StringBuilders
		totLivAreaSB = new StringBuilder();
		marketValSB = new StringBuilder();
		//buildCodeSB = new StringBuilder();
		zipSB = new StringBuilder();

		return zipcodes;
	}

	protected void addToStringBuilder(int columnCount, char c) {
		if (columnCount == total_livable_area)
			totLivAreaSB.append(c);
		if (columnCount == market_value)
			marketValSB.append(c);
//		if (columnCount == building_code)
//			buildCodeSB.append(c);
		if (columnCount == zip_code)
			zipSB.append(c);
	}
	
	
	

	public static void main(String[] args) {
		String filename = "properties.csv";
		//String filename - "practice2.csv";
		 //filename = "practice3_smallZip.csv";
		 //filename = "practice4_smallPropertiesAllGoo.csv";
		//filename = "practice5_moreZip.csv";
		PropertyCSVReader read = new PropertyCSVReader(filename);
		Map<String, PropertyValues> l = read.getPropertyMap();
		Iterator it = l.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue().toString());
		  // it.remove(); // avoids a ConcurrentModificationException
		}
		
		
		

		System.out.println(l.size());
	}
}

