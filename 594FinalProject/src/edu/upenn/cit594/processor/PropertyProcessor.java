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
/**
 * this class processes the property data, uses strategy pattern for the two average method
 * @author Lu and Kai
 *
 */

public class PropertyProcessor implements Processor {
	 protected PropertyCSVReader propertyReader;
	 private Map<String, List<PropertyValues>> propertyMap;
	
	 public PropertyProcessor(PropertyCSVReader propertyReader) {
		 this.propertyReader = propertyReader;
	
	 }
	 
	 //get the property data map from reader
	 @Override
	 public void buildMap() {
		 this.propertyMap = propertyReader.getPropertyMap();
		 
	 }
	 
	 //return the property value based on the type input, if type is "area" return livable area, else return market value
	 public Double getNumber(PropertyValues p, String type) {
		 if(type == "area") {
			 return p.getTotalLivableArea();
		 }
		 else {
			 return p.getMaketValue();
		 }
	 }
	 
	 //calculate the average value based on the zip code input and data type
	 public int Average(String type, String zipCode) {
		Double total = 0.0;
		int average;
		List<PropertyValues> l = new ArrayList<PropertyValues>();
		//iterate the map key set, find the key which is the input zip code, iterate the property values in the zip code and sum the values
		for(String s : propertyMap.keySet()) {
			if(s.equals(zipCode)) {
				l = propertyMap.get(s);
				for(PropertyValues p : l) {
					total += getNumber(p, type);
				}
			}
		}
		//if there is no value associate with the zip code, return 0
		if(l.size() != 0) {
			average = (int) (total/l.size());
		}
		else {
			average = 0;
		}
		return average;	
	 }
	 
	 //apply average method with input data type, return market value data 
	 public int averageResidentialMarketValue(String zipCode) {
		 return  Average("value", zipCode);
	 }
	 
	//apply average method with input data type, return livable area data
	 public int averageResidentialTotalLivableArea(String zipCode) {
		 return  Average("area", zipCode);
	 }
	 
	 // calculate the total residential value per capita, taking the input of zip code, population map data, and the data type should returned
	 public int totalResidentialPerCaptia(String zipCode, Map<String, Integer> populationMap, String type) {
		 int average;
		 Double total = 0.0;
		 int population = 0;
		 List<PropertyValues> l = new ArrayList<PropertyValues>();
		 for(String s : propertyMap.keySet()) {
			if(s.equals(zipCode)) {
				l = propertyMap.get(s);
				for(PropertyValues p : l) {
					total += getNumber(p, type);
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
	     average = (int) (total/population);
		 return average;
	 }
	 
	 //apply totalResidentialPerCaptia method with data type
	 public int totalResidentialMarketValuePerCapita(String zipCode, Map<String, Integer> populationMap){
		 return totalResidentialPerCaptia(zipCode, populationMap, "value");
	 }	
    
	 //apply totalResidentialPerCaptia method with data type
	 public int totalResidentialLivableAreaPerCapita(String zipCode, Map<String, Integer> populationMap){
		 return totalResidentialPerCaptia(zipCode, populationMap, "area");
	 }	
}
