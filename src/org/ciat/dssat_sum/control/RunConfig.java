package org.ciat.dssat_sum.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


import org.ciat.dssat_sum.control.OverviewWorker;
import org.ciat.dssat_sum.model.SummaryRun;

public class RunConfig {
	private SummaryRun run;
	
	@SuppressWarnings("unused")
	public void loadConfig(File config){

		/* Tag the run with the timestamp*/
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date resultdate = new Date(yourmilliseconds);
		String runName = sdf.format(resultdate);
		
		
		try(Scanner reader = new Scanner(config)) {
			
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
			
			run = new SummaryRun(modelShortName, runName, fileT);
			
			//reader.close();
		} catch (FileNotFoundException e) {
			App.LOG.severe("Configuration not found in: "+ config.getAbsolutePath());
		}catch (Exception e) {
			App.LOG.severe("Error reading configuration in file, please check the format: "+ config.getAbsolutePath());
		}


	
	}
	

	public OverviewWorker getOverviewWorker() {
		return new OverviewWorker(run);
	}

	public SeriesWorker getSeriesWorker() {
		return new SeriesWorker(run);
	}



}
