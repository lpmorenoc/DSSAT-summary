package org.ciat.gavilan.model;

import java.io.File;
import java.text.SimpleDateFormat;

public class SummaryRun {

	public static final String LINE_SEPARATOR = "\t";
	public static final String PATH_SEPARATOR = "\\";
	public static final String CANDIDATE_LABEL = "run";
	public static final String DATE_LABEL = "date";
	public static final String TREATMENT_LABEL = "treatment";
	public static final String MEASURED_PREFIX = "meas.";
	public static final String SIMULATED_PREFIX = "out.";
	public static final String COEFFICIENT_PREFIX = "in.";
	public static final String KIBANA_INDEX = "brute.";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd"); // ISO 8601
	private CropCode model;
	private String runName;
	private File overviewCSVOutput;
	private File overviewJSONOutput;
	private File summaryCSVOutput;
	private File summaryJSONOutput;
	private File summaryEvalOutput;



	public SummaryRun(String crop, String cropCode, String runName) {
		super();
		String outputsFolder="out/runName/";
		this.model = CropCode.getModelCode(cropCode);
		this.runName = runName;
		this.overviewCSVOutput = new File(outputsFolder + "overview.csv");
		this.overviewJSONOutput = new File(outputsFolder + "overview.json");
		this.summaryCSVOutput = new File(outputsFolder + "summary.csv");
		this.summaryJSONOutput = new File(outputsFolder + "summary.json");
		this.summaryEvalOutput = new File(outputsFolder + "eval.json");
	}
	

	public CropCode getModel() {
		return model;
	}

	public void setModel(CropCode model) {
		this.model = model;
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

	public File getSummaryEvalOutput() {
		return summaryEvalOutput;
	}

	public String getRunName() {
		return runName;
	}


}
