package edu.upenn.cit594.processor;

import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PropertyCSVReader;
import edu.upenn.cit594.data.PropertyValues;

public class StrategyChooser implements Processor {
	protected Map<String, List<PropertyValues>> propertyMap;
	private Strategy strategy;
	private PropertyCSVReader propertyReader;

	// get the property data map from reader
	@Override
	public void buildMap() {
		this.propertyMap = propertyReader.getPropertyMap();
	}

	public StrategyChooser(PropertyCSVReader propertyReader) {
		this.propertyReader = propertyReader;
		// buildMap(); // build propertyMap
	}

	public int chooseStrategy(Map<String, Integer> popMap, String zipcode, Strategy strategy) {
		this.strategy = strategy;
		return executeStrategy(popMap, zipcode);
	}

	private int executeStrategy(Map<String, Integer> popMap, String zipcode) {

		/// System.out.println(propertyMap.toString());
		return strategy.doOperation(propertyMap, popMap, zipcode);

	}
}
