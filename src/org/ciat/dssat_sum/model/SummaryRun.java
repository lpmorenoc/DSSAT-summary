package org.ciat.dssat_sum.model;

import java.io.File;

public class SummaryRun {

	private String model;
	public final String LINE_SEPARATOR = "\t";
	public final String PATH_SEPARATOR = "\\";
	private File overviewOutput;
	private File summaryOutput;
	private String fileT;

	public SummaryRun(String model, String runName, String fileT) {
		super();
		this.setModel(model);
		this.overviewOutput = new File("overview_"+runName+".csv");
		this.summaryOutput = new File("summary_"+runName+".csv");
		this.setFileT(fileT);
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



	public String getFileT() {
		return fileT;
	}



	public void setFileT(String fileT) {
		this.fileT = fileT;
	}


	



	

}
