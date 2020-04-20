package edu.upenn.cit594.ui;




import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

/**
 * this is user interface class, it is responsible for the interacting with user and processing data as it relates to display it.
 *
 */

public class UserInterface {
	protected Processor processor;
	protected Logger log;
	
	public UserInterface(Processor processor, Logger log) {
		this.processor = processor;
		this.log = log;
	}
	
}
