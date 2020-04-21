package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class ViolationJsonReader implements ViolationReader{

	

	protected String filename;


	public ViolationJsonReader(String name) {
		filename = name;
	}

	// gets all data from file
	@SuppressWarnings("rawtypes")
	public Map getViolationMap()  {
		Map<Integer, Integer> zipcodesWithParking = new HashMap<Integer, Integer>();
		
	// check file permissions and open
		File f = ErrorChecker.checkReadability(filename);
		try {

			// create a parser
			JSONParser parser = new JSONParser();
			// open the file and get the array of JSON objects
			JSONArray violations = (JSONArray)parser.parse(new FileReader(filename));
			// use an iterator to iterate over each element of the array
			Iterator iter = violations.iterator();
			// iterate while there are more objects in array
			while (iter.hasNext()) {
				
				
			// get the next JSON object
			JSONObject violation = (JSONObject) iter.next();
			// use the "get" method to print the value associated with that key
			// System.out.println(violation.get("state"));
			
			
     if(violation.get("state").toString().equals("PA")) {
			//int zipcode = Integer.parseInt(violation.get("zip_code").toString());
    	 String zip_code = violation.get("zip_code").toString();
    	 
			fillInMap(zipcodesWithParking, zip_code);
			//System.out.println(violation.get("state"));
			}
     
		}

		} catch (Exception e) {
			System.out.println("ERROR: jsonReader");
			
		}
//		System.out.println("THE COUNT IS " + count);
		return zipcodesWithParking;

	}
	
	protected void fillInMap(Map<Integer, Integer> zipcodesWithParking, String zip_code) {
		if(zip_code.length() == 5) {	
		if( !zipcodesWithParking.containsKey(Integer.parseInt(zip_code))) {
			zipcodesWithParking.put(Integer.parseInt(zip_code), 1);
			}
		else {
			int tempZip = zipcodesWithParking.get(Integer.parseInt(zip_code))+1;
			zipcodesWithParking.put(Integer.parseInt(zip_code), tempZip);
		}
		}
	}
	
public static void main(String[] args) {
	String filename = "parking.json";
	ViolationJsonReader tr = new ViolationJsonReader(filename);
	Map <Integer, Integer> m = tr.getViolationMap();
	@SuppressWarnings("rawtypes")
	Iterator it = m.entrySet().iterator();
	while (it.hasNext()) {
		Map.Entry pair = (Map.Entry) it.next();
	System.out.println(pair.getKey() + " = " + pair.getValue().toString());
	//   it.remove(); // avoids a ConcurrentModificationException
	   
}
	//System.out.println(m.size());

	
}
	
	
}
