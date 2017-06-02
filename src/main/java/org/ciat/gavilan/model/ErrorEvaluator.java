package org.ciat.gavilan.model;

public class ErrorEvaluator {

	public static double RMSE(Double[] observed, Double[] calculated) {
		double rmse = 0.0;
		double n = 0;

		for (int i = 0; i < observed.length; i++) {
			if (isOK(observed[i]) && isOK(calculated[i])) {
				rmse += Math.pow((observed[i] - calculated[i]), 2); // Sum (O-P)^2
				n++;
			}
		}

		rmse /= n; // Sum (O-P)^2 / N
		rmse = Math.sqrt(rmse); // SQRT (Sum (O-P)^2 / N)
		
		if(!Utils.isNumeric(rmse+"")){
			rmse=-1;
		}

		return rmse;
	}

	public static double NSE(Double[] observed, Double[] calculated) {
		double nse = 0.0; // NSE
		double sd = 0.0; // standard deviations
		double o_avg = 0.0; // average of observed
		double n = 0;

		for (int i = 0; i < observed.length; i++) {
			if (isOK(observed[i])) {
				o_avg += observed[i];
				n++;
			}
		}
		
		o_avg /= n; // (Sum O)/N

		for (int i = 0; i < observed.length; i++) {
			if (isOK(observed[i])) {
				sd += Math.pow((observed[i] - o_avg), 2); // Sum (O-O_avg)^2
			}
		}
		nse = 1 - Math.pow((RMSE(observed, calculated) / Math.sqrt(sd)),2);

		if(!Utils.isNumeric(nse+"")){
			nse=-1;
		}
		return nse;
	}

	private static boolean isOK(Double value) {
		return value != null && value != -99 && value != 0;
	}

}
