package edu.upenn.cit594.datamanagement;

import java.util.HashMap;



public interface ViolationReader extends Reader{

	public HashMap<String, Double> getViolationMap();

}
