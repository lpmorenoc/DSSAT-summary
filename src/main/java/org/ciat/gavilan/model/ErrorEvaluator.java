package org.ciat.gavilan.model;

public class ErrorEvaluator {

	public static double RMSE(Double[] observed, Double[] simulated) {
		double rmse = 0.0;

		for (int i = 0; i < observed.length; i++) {
			rmse += Math.pow((observed[i] - simulated[i]), 2); // Sum (O-P)^2
		}

		rmse /= observed.length; // Sum (O-P)^2 / N
		rmse = Math.sqrt(rmse); // SQRT (Sum (O-P)^2 / N)

		return rmse;
	}

	public static double NSE(Double[] observed, Double[] simulated) {
		double nse = 0.0; // NSE
		double rmse = 0.0; // RMSE
		double sd = 0.0; // standard deviations
		double o_avg = 0.0; // average of observed

		for (int i = 0; i < observed.length; i++) {
			nse += Math.pow((observed[i] - simulated[i]), 2); // Sum (O-P)^2
			o_avg += observed[i];
		}
		o_avg /= observed.length; // (Sum O)/N

		for (int i = 0; i < observed.length; i++) {
			// Sum (O-O_avg)^2
			sd += Math.pow((observed[i] - o_avg), 2);
		}
		nse = 1 - (rmse / sd);

		return nse;
	}

}
