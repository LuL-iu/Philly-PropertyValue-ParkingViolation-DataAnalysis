package edu.upenn.cit594.datamanagement;
import java.util.HashMap;

public class PopulationReader implements CSVReader, Reader{
	public HashMap<Integer, Integer> populationMap;
	
	public HashMap<Integer, Integer> getPopulationMap(){
		return populationMap;
		
	}
}
