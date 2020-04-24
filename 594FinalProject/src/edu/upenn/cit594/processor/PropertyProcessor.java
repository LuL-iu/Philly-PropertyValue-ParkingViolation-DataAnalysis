package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyCSVReader;
import edu.upenn.cit594.datamanagement.ViolationReader;
import edu.upenn.cit594.logging.Logger;

public class PropertyProcessor implements Processor {
	 protected PropertyCSVReader propertyReader;
	 private Map<String, List<PropertyValues>> propertyMap;
	
	 
	 public PropertyProcessor(PropertyCSVReader propertyReader, Logger log) {
		 this.propertyReader = propertyReader;
	
	 }
	 
	 public void buildMap() {
		 this.propertyMap = propertyReader.getPropertyMap();
	 }

	 public int Average(String type, String zipCode) {
		int total = 0;
		int average;
		List<PropertyValues> l = new ArrayList();
		for(String s : propertyMap.keySet()) {
			if(s.equals(zipCode)) {
				l = propertyMap.get(s);
				for(PropertyValues p : l) {
					if(type.contentEquals("area")) {
						total += p.getTotalLivableArea();
					}
					if(type.contentEquals("marketvalue")) {
						total += p.getMaketValue();
					}
				}
			}
		}
//		System.out.println("total is " + total + "l size is " + l.size());
		if(l.size() != 0) {
			average = total/l.size();
		}
		else {
			average = 0;
		}
		return average;	
	 }
	 
	 public int averageResidentialMarketValue(String zipCode) {
		 return  Average("marketvalue", zipCode);
	 }
	 
	 public int averageResidentialTotalLivableArea(String zipCode) {
		 return  Average("area", zipCode);
	 }
	 
	 public int totalResidentialMarketValuePerCapita(String zipCode, Map<String, Integer> populationMap){
		 return totalResidentialPerCaptia(zipCode, populationMap, "value");
	 }	
    
	 public int totalResidentialLivableAreaPerCapita(String zipCode, Map<String, Integer> populationMap){
		 return totalResidentialPerCaptia(zipCode, populationMap, "area");
	 }	
	 
	 public int totalResidentialPerCaptia(String zipCode, Map<String, Integer> populationMap, String type) {
		 int average;
		 int total = 0;
		 int population = 0;
		 List<PropertyValues> l = new ArrayList();
		 for(String s : propertyMap.keySet()) {
			if(s.equals(zipCode)) {
				l = propertyMap.get(s);
				for(PropertyValues p : l) {
					if(type.contentEquals("area")) {
						total += p.getTotalLivableArea();
					}
					if(type.contentEquals("value")) {
						total += p.getMaketValue();
					}
				}
			}
		 }
		 for(String s : populationMap.keySet()) {
			if(s.equals(zipCode)) {
				population = populationMap.get(s);
			}
		 }
		 if(population == 0 || total == 0) {
			 return 0;
		 }
	     average = total/population;
		 return average;
	 }
}
