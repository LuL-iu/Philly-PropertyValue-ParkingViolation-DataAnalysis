package edu.upenn.cit594.processor;

import java.util.Map;

import edu.upenn.cit594.datamanagement.PopulationReader;

import edu.upenn.cit594.logging.Logger;

/**
 * this is a main class, which checks the form of arguments, read the arguments,  create other objects and their relationships, then start 
 * the application via the UI.
 *  @author Lu & Kai
 *  
 */

public class PopulationProcessor implements Processor{ 
	 private PopulationReader populationReader;
	 private Map<String, Integer> populationMap;  
	 
	 public PopulationProcessor(PopulationReader populationReader) {
		 this.populationReader = populationReader;
	 }
	 
	 @Override
	 public void buildMap() {
		 this.populationMap = populationReader.getDataFromFile();
	 }
	 
	 public int totalPopulation(){
		 int total = 0;
		 for(String s : populationMap.keySet()) {
			 total += populationMap.get(s);
		 }
		 return total;
	 }
	 
	 public  Map<String, Integer> getPopulationMap () {
		 return populationMap;
	 }
	 
}
