package src.org.ciat.dssat_sum.control;

import java.io.File;

import src.org.ciat.dssat_sum.control.SummaryRunManager;

public class RunConfig {

	public SummaryRunManager getExtractionManager(File config) {
		
		//ArrayList<String> section = new ArrayList<String>();
		//ArrayList<String> variables = new ArrayList<String>();
		
		
		// SummaryRun run= new SummaryRun(section,variables);
		return new SummaryRunManager();
	}

}
