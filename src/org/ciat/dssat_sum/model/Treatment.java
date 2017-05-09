package org.ciat.dssat_sum.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Treatment {

	private int number;
	private Set<Measurement> samplings;

	public Treatment(int number) {
		super();
		this.setNumber(number);
		samplings = new LinkedHashSet<>();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Set<Measurement> getSamplings() {
		return samplings;
	}

	public void setSamplings(Set<Measurement> samplings) {
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
