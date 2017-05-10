package org.ciat.dssat_sum.model;

import java.io.File;

public class SummaryRun {

	private ModelCode model;
	public final String LINE_SEPARATOR = "\t";
	public final String PATH_SEPARATOR = "\\";
	private File overviewOutput;
	private File summaryOutput;
	private String fileT;

	public SummaryRun(String model, String runName, String fileT) {
		super();
		this.setModel(ModelCode.getModelCode(model));
		this.overviewOutput = new File(runName+"_overview.csv");
		this.summaryOutput = new File(runName+"_summary.csv");
		this.setFileT(fileT);
	}

	public ModelCode getModel() {
		return model;
	}

	public void setModel(ModelCode model) {
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
