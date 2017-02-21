package org.ciat.dssat_sum.model;

import java.util.ArrayList;

public class SummaryRun {
	private ArrayList<String> section;
	private ArrayList<String> variables;

	public SummaryRun(ArrayList<String> section, ArrayList<String> variables) {
		super();
		this.section = section;
		this.variables = variables;
	}

	public ArrayList<String> getSection() {
		return section;
	}

	public ArrayList<String> getVariables() {
		return variables;
	}

}
