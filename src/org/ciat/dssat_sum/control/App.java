package src.org.ciat.dssat_sum.control;

import java.io.File;

import src.org.ciat.dssat_sum.control.RunConfig;
import src.org.ciat.dssat_sum.control.SummaryRunManager;

public class App {

	public static void main(String[] args) {

		SummaryRunManager mgr;
		File config = new File("config.txt");
		RunConfig rc = new RunConfig();

		//if (config.exists()) {

			mgr = rc.getExtractionManager(config);
			mgr.work();

		//}
	}

}
