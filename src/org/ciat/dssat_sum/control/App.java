package org.ciat.dssat_sum.control;

import java.io.File;
import org.ciat.dssat_sum.control.SummaryRunManager;
import org.ciat.dssat_sum.control.RunConfig;

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
