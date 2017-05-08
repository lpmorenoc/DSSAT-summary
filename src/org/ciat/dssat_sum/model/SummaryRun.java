package org.ciat.dssat_sum.model;

import java.io.File;

public class SummaryRun {

	private String model;
	public final String LINE_SEPARATOR = "\t";
	public final String PATH_SEPARATOR = "\\";
	private File overviewOutput;
	private File summaryOutput;

	public SummaryRun(String model, String runName) {
		super();
		this.setModel(model);
		this.overviewOutput = new File("overview-"+runName+".csv");
		this.summaryOutput = new File("summary-"+runName+".csv");
	}

	

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}


	public File getOverviewOutput() {
		return overviewOutput;
	}

	public File getSummaryOutput() {
		return summaryOutput;
	}


	



	

}
