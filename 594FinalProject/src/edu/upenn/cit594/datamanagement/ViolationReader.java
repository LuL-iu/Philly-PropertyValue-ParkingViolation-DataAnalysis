package edu.upenn.cit594.datamanagement;


import java.util.Map;



public interface ViolationReader extends Reader{

	public Map<String, Double> getViolationMap();

}
