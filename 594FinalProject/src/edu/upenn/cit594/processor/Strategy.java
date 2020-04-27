package edu.upenn.cit594.processor;

import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.PropertyValues;

public interface Strategy {
	public int doOperation(Map<String, List<PropertyValues>> propertyMap, Map<String, Integer> popMap, String zipcode);
}
