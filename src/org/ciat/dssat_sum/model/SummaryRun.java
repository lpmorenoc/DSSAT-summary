package org.ciat.dssat_sum.model;

import java.io.File;

public class SummaryRun {

	private ModelCode model;
	public static final String LINE_SEPARATOR = "\t";
	public static final String PATH_SEPARATOR = "\\";
	public static final String CANDIDATE_LABEL="RUN";
	public static final String DATE_LABEL="DATE";
	public static final String TREATMENT_LABEL="TR";
	private File overviewCSVOutput;
	private File overviewJSONOutput;
	private File summaryCSVOutput;
	private File summaryJSONOutput;
	private String fileT;

	public SummaryRun(String model, String runName, String fileT) {
		super();
		this.setModel(ModelCode.getModelCode(model));
		this.overviewCSVOutput = new File(runName+"_overview.csv");
		this.overviewJSONOutput = new File(runName+"_overview.json");
		this.summaryCSVOutput = new File(runName+"_summary.csv");
		this.summaryJSONOutput = new File(runName+"_summary.json");
		this.setFileT(fileT);
	}

	public ModelCode getModel() {
		return model;
	}

	public void setModel(ModelCode model) {
		this.model = model;
	}

	public String getFileT() {
		return fileT;
	}

	public void setFileT(String fileT) {
		this.fileT = fileT;
	}

	public File getOverviewCSVOutput() {
		return overviewCSVOutput;
	}

	public File getSummaryCSVOutput() {
		return summaryCSVOutput;
	}

	public File getSummaryJSONOutput() {
		return summaryJSONOutput;
	}

	public File getOverviewJSONOutput() {
		return overviewJSONOutput;
	}


	



	

}
