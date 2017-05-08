package org.ciat.dssat_sum.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.ciat.dssat_sum.control.OverviewWorker;
import org.ciat.dssat_sum.control.RunConfig;
import org.ciat.dssat_sum.model.LogFormatter;
import org.ciat.dssat_sum.model.SummaryRun;
import org.ciat.dssat_sum.control.OverviewWorker.fileSection;

public class App {

	public static String runName;
	public static Logger LOG = obtainLogger();
	
	public static void main(String[] args) {

		OverviewWorker owrk;
		SeriesWorker swrk;
		RunConfig rc = new RunConfig();

		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date resultdate = new Date(yourmilliseconds);
		runName = sdf.format(resultdate);

		Logger log = obtainLogger();

		SummaryRun sr = new SummaryRun(obtainModel(), runName);
		log.info("work started");

		owrk = rc.getOverviewWorker(sr);
		owrk.work();

		swrk = rc.getSeriesWorker(sr);
		swrk.work();

		log.info("work finished");

	}

	private static String obtainModel() {
		String model = "MZCER046 - Maize";
		File firstCultivarOutput = new File(((new File("0")).listFiles()[0].getAbsolutePath() + "\\OVERVIEW.OUT"));
		Scanner reader;
		try {
			if (firstCultivarOutput.exists()) {
				reader = new Scanner(firstCultivarOutput);
				String line = "";
				fileSection flag = fileSection.INIT;
				while (flag == fileSection.INIT && reader.hasNextLine()) {
					line = reader.nextLine();
					if (line.contains("MODEL          :")) {
						model = line.substring(18, 38);
						flag = fileSection.END;
					}

				}
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LOG.info("Detected model: " + model);
		return model;
	}

	private static Logger obtainLogger() {
		FileHandler fileHandler;
		ConsoleHandler consoleHandler;
		LogFormatter formatterTxt;
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		Handler[] handlers = logger.getHandlers();
		for(Handler handler : handlers) {
			logger.removeHandler(handler);
		}
		try {
			fileHandler = new FileHandler("log" + runName + ".log");
			consoleHandler = new ConsoleHandler();
			formatterTxt = new LogFormatter();
			fileHandler.setFormatter(formatterTxt);
			consoleHandler.setFormatter(formatterTxt);
			logger.addHandler(fileHandler);
			logger.addHandler(consoleHandler);
			logger.setUseParentHandlers(false);
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logger;
	}

}
