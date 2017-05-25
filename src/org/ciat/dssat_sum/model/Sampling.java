package org.ciat.dssat_sum.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Sampling {
	
	private Map<Variable, Double> values;

	public Sampling() {
		super();
		this.values = new LinkedHashMap<Variable, Double>();
	}
	

	public Map<Variable, Double> getValues() {
		return values;
	}

	public void setValues(Map<Variable, Double> values) {
		this.values = values;
	}
	


}
