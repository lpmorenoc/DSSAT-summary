package org.ciat.gavilan.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Treatment {

	private int number;
	private Map<String, Sampling> samplings; // the key is for a normalized date

	public Treatment(int number) {
		super();
		this.setNumber(number);
		samplings = new LinkedHashMap<>();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Map<String, Sampling>  getSamplings() {
		return samplings;
	}

	public void setSamplings(Map<String, Sampling>  samplings) {
		this.samplings = samplings;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Treatment))
			return false;
		Treatment castedObj = (Treatment) obj;
		if (castedObj.number == this.number) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
}
