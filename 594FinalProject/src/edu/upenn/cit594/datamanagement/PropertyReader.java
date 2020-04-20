package edu.upenn.cit594.datamanagement;

import java.util.HashMap;
import java.util.Map;

import edu.upenn.cit594.data.PropertyValues;

public interface PropertyReader extends Reader{

	public HashMap<Integer, PropertyValues> getPropertyMap();

}
