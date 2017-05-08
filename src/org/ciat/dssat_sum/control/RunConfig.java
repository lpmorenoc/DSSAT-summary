package org.ciat.dssat_sum.control;


import org.ciat.dssat_sum.control.OverviewWorker;
import org.ciat.dssat_sum.model.SummaryRun;

public class RunConfig {
	

	public OverviewWorker getOverviewWorker(SummaryRun run) {

		return new OverviewWorker(run);
	}

	public SeriesWorker getSeriesWorker(SummaryRun run) {
		return new SeriesWorker(run);
	}



}
