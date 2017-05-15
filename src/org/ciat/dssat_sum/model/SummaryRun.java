package org.ciat.dssat_sum.model;

import java.io.File;
import java.text.SimpleDateFormat;

public class SummaryRun {

	private ModelCode model;
	public static final String LINE_SEPARATOR = "\t";
	public static final String PATH_SEPARATOR = "\\";
	public static final String CANDIDATE_LABEL = "RUN";
	public static final String DATE_LABEL = "DATE";
	public static final String TREATMENT_LABEL = "TR";
	public static final String MEASURED_PREFIX = "M_";
	public static final String SIMULATED_PREFIX = "S_";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd"); // ISO 8601
	private File overviewCSVOutput;
	private File overviewJSONOutput;
	private File summaryCSVOutput;
	private File summaryJSONOutput;
	private String fileT;
	private String fileA;

	public SummaryRun(String model, String runName, String fileT, String fileA) {
		super();
		this.setModel(ModelCode.getModelCode(model));
		this.overviewCSVOutput = new File(runName + "_overview.csv");
		this.overviewJSONOutput = new File(runName + "_overview.json");
		this.summaryCSVOutput = new File(runName + "_summary.csv");
		this.summaryJSONOutput = new File(runName + "_summary.json");
		this.fileT = fileT;
		this.fileA = fileA;
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

	public String getFileA() {
		return fileA;
	}

	public void setFileA(String fileA) {
		this.fileA = fileA;
	}

}
