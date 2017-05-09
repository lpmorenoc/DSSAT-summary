package org.ciat.dssat_sum.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Measurement {
	
	private Date date;
	private Map<Variable, Double> values;

	public Measurement(Date date) {
		super();
		this.date = date;
		this.values = new LinkedHashMap<Variable, Double>();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<Variable, Double> getValues() {
		return values;
	}

	public void setValues(Map<Variable, Double> values) {
		this.values = values;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Measurement))
			return false;
		Measurement castedObj = (Measurement) obj;
		if (castedObj.date == this.date) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(date);
	}

}
