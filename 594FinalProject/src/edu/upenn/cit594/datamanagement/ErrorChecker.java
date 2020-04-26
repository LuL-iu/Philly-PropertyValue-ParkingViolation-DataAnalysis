package edu.upenn.cit594.datamanagement;

import java.io.File;

/**
 * this is a error checker class, which checks the file with provided file is exist or not, it also check whether a zip code is valid zip code or a number is integer or double
 * 
 *  @author Kai & Lu
 *  
 */

//Does the system fail when wrong file is inputted by user?
public class ErrorChecker {


	// checks if file is readable
	public boolean checkReadability(String fileName) {	
		File f = null;
		boolean fileReads = false;
		f = new File(fileName);
		// true if file exists
		fileReads = f.exists();
		// if file exists
		if (fileReads) {
			// read permissions are possible?
			fileReads = f.setReadable(true, true);
			// is file readable?
			fileReads = f.canRead();
		}
		if (fileReads == false) {
			System.out.println("ERROR:  " + fileName + "  file is unreadable or does not exist");
			System.exit(0);
		}
		return true;

	}
	
	// check if String is a number or if it's null
	public boolean isNumber(String dataFromFile) {
		if (dataFromFile == null)
			return false;
		try {
			Double.parseDouble(dataFromFile);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	// check if String is a number or if it's null
	public boolean isInteger(String dataFromFile) {
		if (dataFromFile == null)
			return false;
		try {
			Integer.parseInt(dataFromFile);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}


	// check if zipcode is valid
	public boolean isValidZip(String zipcode) {
		if (zipcode.length() >= 5 && isInteger(zipcode)) {
			return true;
		}
		return false;
	}
	
	// check if zipcode is valid
	public boolean is5DigitZip(String zipcode) {
		if (zipcode.length() == 5 && isInteger(zipcode)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
	// TODO Auto-generated method stub
	String fileName = "population.txt";
	ErrorChecker eCheck = new ErrorChecker();
	System.out.println(eCheck.checkReadability(fileName));
	}
}
