package org.ciat.dssat_sum.model;

import java.util.List;

public class Section {
	
	private String name;
	private List<SerieVariable> variables;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SerieVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<SerieVariable> variables) {
		this.variables = variables;
	}
}
