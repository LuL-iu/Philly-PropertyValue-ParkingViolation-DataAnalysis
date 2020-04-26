package edu.upenn.cit594.processor;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.ViolationReader;
import edu.upenn.cit594.logging.Logger;
/**
 * this class processes the parking fine data
 * @author Lu and Kai
 *
 */

public class ViolationProcessor implements Processor{
	 protected ViolationReader violationReader;  ;
	 private Map<String, Double> violationMap;
	 
	 
	 public ViolationProcessor(ViolationReader violationReader) {
		 this.violationReader = violationReader;
	 }
	 
	 // get the parking fine data from readear
	 @Override
	 public void buildMap() {
		 this.violationMap = violationReader.getViolationMap();
	 }
	 
	 // calculate the parking fine per capita and add the data info to a tree map
	 public TreeMap<String, Double> totalFinesPerCapita(Map<String, Integer> populationMap){
		 TreeMap<String, Double> totalFinesPerCapita = new TreeMap<String, Double>();
		 for(String s: violationMap.keySet()) {
	         double fine =  0.0;
	         int population = 0;
			 if(populationMap.containsKey(s)) {
				 population = populationMap.get(s);
			 }
			 if(violationMap.containsKey(s)) {
				 fine = violationMap.get(s);
			 }
			 if(population != 0 && fine != 0) {
				 double finePerCapita = fine/population;
				 finePerCapita = Math.floor(finePerCapita * 10000)/10000;
				 totalFinesPerCapita.put(s, finePerCapita); 
			 }
		 }
		 return totalFinesPerCapita;
	 }
	 
	// iterate the violation map, find the zip code with highest total parking fine
	 public String getHighestFineLocation(){
		 String zipcode = "";
		 Double max = 0.0;
		 for(String s: violationMap.keySet()) {
			 if(violationMap.get(s) > max) {
				 max = violationMap.get(s);
				 zipcode = s;
			 }
		 }
		 return zipcode;	 
	 }
	 
	
}
