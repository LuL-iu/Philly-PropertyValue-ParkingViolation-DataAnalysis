package edu.upenn.cit594.datamanagement;

import java.io.File;

//Does the system fail when wrong file is inputted by user?
public class ErrorChecker {


	// checks if file is readable
	public static File checkReadability(String filename) {
		File f = null;

		boolean fileReads = false;
		try {

			f = new File(filename);

			// true if file exists
			fileReads = f.exists();

			// if file exists
			if (fileReads) {

				// read permissions are possible?
				fileReads = f.setReadable(true, true);

				// is file readable?
				fileReads = f.canRead();
			}

		} catch (Exception e) {

			System.out.println("ERROR: " + filename + " file is unreadable or does not exist");
			System.exit(0);
		}
		if (fileReads == false) {
			System.out.println("ERROR:  " + filename + "  file is unreadable or does not exist");
			System.exit(0);
		}
		return f;

	}
	
	// check if String is a number or if it's null
		public static boolean isNumber(String dataFromFile) {
			if (dataFromFile == null)
				return false;
			try {
				Double.parseDouble(dataFromFile);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		// check if zipcode is valid
		public static boolean isValidZip(String zipcode) {
			if (zipcode.length() >= 5 && isNumber(zipcode)) {
				zipcode = zipcode.substring(0, 5);
				// zip_code = zipcode;
				return true;
			}
			return false;
		}

}
