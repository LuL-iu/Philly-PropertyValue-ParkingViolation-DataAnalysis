package edu.upenn.cit594.processor;

import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.PropertyValues;

//livable area per capita
public class LivableAreaPerCapitaStrategyPattern implements Strategy { // per capita liv area

	public int doOperation(Map<String, List<PropertyValues>> propertyMap, Map<String, Integer> popMap, String zipcode) {
		int total = 0;
		if (propertyMap.containsKey(zipcode)) {
			for (PropertyValues pv : propertyMap.get(zipcode)) {
				total += pv.getTotalLivableArea();
			}
		}

		if (popMap.containsKey(zipcode)) {
			int population = popMap.get(zipcode);
			return total / population;
		}
		return 0;
	}

}
