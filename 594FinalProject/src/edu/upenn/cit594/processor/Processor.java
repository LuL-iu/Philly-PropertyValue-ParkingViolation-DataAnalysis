package edu.upenn.cit594.processor;

import java.util.HashMap;

import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.ViolationReader;

public class Processor {
	 protected Reader violationReader, propertyReader, populationReader;
	 public HashMap<Integer, Integer> populationMap;
	 public HashMap<Integer, PropertyValues> propertyMap;
	 public HashMap<Integer, Integer> violationMap;
	 public int totalMarketValues;
	 
	 public Processor(ViolationReader violationReader, PropertyReader propertyReader, PopulationReader populationReader) {
		 this.violationReader = violationReader;
		 this.propertyReader = propertyReader;
		 this.populationReader = populationReader;
		 populationMap = populationReader.getPopulationMap();
		 propertyMap = propertyReader.getPropertyMap();
		 violationMap = violationReader.getViolationMap();
	 }
	 
	 public int totalPopulation(){
		 	return 0;
	 }
	 
	 public HashMap<Integer, Integer> totalFinesPerCaptia(HashMap<Integer, Integer> map){
		 return null;
	 }
	 
	 public double Average(String type) {
		int total = 0;
		for(Integer i : propertyMap.keySet()) {
			if(type == "area") {
				total += propertyMap.get(i).totalLivableArea;
			}
			if(type == "marketvalue") {
				total += propertyMap.get(i).maketValue;
			}
		}
		double average = total/propertyMap.keySet().size();
		if(type.equals("marketvalue")) {
			totalMarketValues = total;
		}
		return average;	
	 }
	 
	 public double averageResidentialMarketValue() {
		 return  Average("marketvalue");
	 }
	 
	 public double averageResidentialTotalLivableArea() {
		 return  Average("area");
	 }
	 
	 public double totalResidentialMarketValuePerCapita (){
		 return 0.0;
	 }	
}


