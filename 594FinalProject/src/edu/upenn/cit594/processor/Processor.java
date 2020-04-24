package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyCSVReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.ViolationReader;
import edu.upenn.cit594.logging.Logger;

public class Processor {
	 protected ViolationReader violationReader; 
	 protected PropertyCSVReader propertyReader;
	 protected PopulationReader populationReader;
	 private Map<String, Integer> populationMap;
	 private Map<String, List<PropertyValues>> propertyMap;
	 private Map<String, Double> violationMap;
	 
	 
	 public Processor(ViolationReader violationReader, PropertyCSVReader propertyReader, PopulationReader populationReader, Logger log) {
		 this.violationReader = violationReader;
		 this.propertyReader = propertyReader;
		 this.populationReader = populationReader;
	 }
	 
	 public void SetUp() {
		 this.populationMap = populationReader.getDataFromFile();
		 this.violationMap = violationReader.getViolationMap();
		 this.propertyMap = propertyReader.getPropertyMap();
	 }
	 
	 
	 
	 
	 public int totalPopulation(){
		 int total = 0;
		 for(String s : populationMap.keySet()) {
			 total += populationMap.get(s);
		 }
		 return total;
	 }
	 
	 public TreeMap<String, Double> totalFinesPerCaptia(){
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
	 
	 public int totalResidentialMarketValuePerCapita(String zipCode){
		 int average;
		 int total = 0;
		 int population = 0;
		 List<PropertyValues> l = new ArrayList();
		 for(String s : propertyMap.keySet()) {
			if(s.equals(zipCode)) {
				l = propertyMap.get(s);
				for(PropertyValues p : l) {
					total += p.getMaketValue();
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
	 
//	 public static void main(String[] args) {
//			Double i = 131232.002666555;
//			i = Math.floor(i * 10000)/ 10000;
//			System.out.println(i);
//		double i = 2000.000;
//		double a = i/3;
//		System.out.println(a);
//	 }

}


