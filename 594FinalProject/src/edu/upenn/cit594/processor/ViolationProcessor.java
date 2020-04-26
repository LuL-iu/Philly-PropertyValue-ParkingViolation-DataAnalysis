package edu.upenn.cit594.processor;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.ViolationReader;
import edu.upenn.cit594.logging.Logger;

public class ViolationProcessor implements Processor{
	 protected ViolationReader violationReader;  ;
	 private Map<String, Double> violationMap;
	 
	 
	 public ViolationProcessor(ViolationReader violationReader) {
		 this.violationReader = violationReader;
	 }
	 
	 @Override
	 public void buildMap() {
		 this.violationMap = violationReader.getViolationMap();
	 }
	 
	 public TreeMap<String, Double> totalFinesPerCaptia(Map<String, Integer> populationMap){
		 TreeMap<String, Double> totalFinesPerCapita = new TreeMap();
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
