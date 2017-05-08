package org.ciat.dssat_sum.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


import org.ciat.dssat_sum.control.OverviewWorker;
import org.ciat.dssat_sum.model.SummaryRun;

public class RunConfig {
	private SummaryRun run;
	
	@SuppressWarnings("unused")
	public void loadConfig(String config){

		/* Tag the run with the timestamp*/
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date resultdate = new Date(yourmilliseconds);
		String runName = sdf.format(resultdate);
		
		Scanner reader = new Scanner(config);

		String modelName = reader.nextLine();
		String modelShortName = reader.nextLine();
		String fileA = reader.nextLine();
		String fileT = reader.nextLine();
		String fileXHead = reader.nextLine();
		String fileXTail = reader.nextLine();
		String culHead = reader.nextLine();
		String maxFiles = reader.nextLine();
		String vrname = reader.nextLine();
		String eco = reader.nextLine();
		String domi = "";

		run = new SummaryRun(modelName, runName, fileT);

		reader.close();

	
	}
	

	public OverviewWorker getOverviewWorker() {
		return new OverviewWorker(run);
	}

	public SeriesWorker getSeriesWorker() {
		return new SeriesWorker(run);
	}



}
