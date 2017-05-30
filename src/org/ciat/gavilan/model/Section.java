package org.ciat.gavilan.model;

import java.util.List;

public class Section {
	
	private String name;
	private List<VariableLocation> variables;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VariableLocation> getVariables() {
		return variables;
	}

	public void setVariables(List<VariableLocation> variables) {
		this.variables = variables;
	}
}
