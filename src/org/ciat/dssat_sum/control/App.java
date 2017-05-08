package org.ciat.dssat_sum.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ciat.dssat_sum.control.OverviewWorker;
import org.ciat.dssat_sum.control.RunConfig;
import org.ciat.dssat_sum.model.LogFormatter;

public class App {

	public static Logger LOG = obtainLogger();
	
	public static void main(String[] args) {
		App app= new App();
		app.run();
	}
	
	private void run() {

		OverviewWorker owrk;
		SeriesWorker swrk;
		RunConfig rc = new RunConfig();

		
		LOG.fine("work started");
		
		rc.loadConfig("config.txt");

		owrk = rc.getOverviewWorker();
		owrk.work();

		swrk = rc.getSeriesWorker();
		swrk.work();

		LOG.fine("work finished");
		
	}





	private static Logger obtainLogger() {
		/* Tag the log with the timestamp*/
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date resultdate = new Date(yourmilliseconds);
		String runName = sdf.format(resultdate);
		
		FileHandler fileHandler;
		ConsoleHandler consoleHandler;
		LogFormatter formatterTxt;
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		Handler[] handlers = logger.getHandlers();
		for(Handler handler : handlers) {
			logger.removeHandler(handler);
		}
		try {
			fileHandler = new FileHandler("log_" + runName + ".log");
			consoleHandler = new ConsoleHandler();
			fileHandler.setLevel(Level.FINE);
			consoleHandler.setLevel(Level.FINE);
			formatterTxt = new LogFormatter();
			fileHandler.setFormatter(formatterTxt);
			consoleHandler.setFormatter(formatterTxt);
			logger.addHandler(fileHandler);
			logger.addHandler(consoleHandler);
			logger.setUseParentHandlers(false);
			logger.setLevel(Level.ALL);

			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logger;
	}

}
