package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValues;

public class GetPropertyLivableArea implements GetPropertyValue {
	@Override
	public double getValue(PropertyValues p) {
		return p.getTotalLivableArea();
	};
}
