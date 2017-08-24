package org.ciat.gavilan.model;

import java.util.Map;

public class ErrorEvaluator {

	public static double RMSE(Map<Integer,Double> observed, Map<Integer,Double> calculated) {
		double rmse = 0.0;
		double n = 0;

		for (Integer i:observed.keySet()) {
			if (isOK(observed.get(i)) && isOK(calculated.get(i))) {
				rmse += Math.pow((observed.get(i) - calculated.get(i)), 2); // Sum (O-P)^2
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

	public static double NSE(Map<Integer,Double> observed, Map<Integer,Double> calculated) {
		double nse = 0.0; // NSE
		double sd = 0.0; // standard deviations
		double o_avg = 0.0; // average of observed
		double n = 0;

		for (Integer i:observed.keySet()) {
			if (isOK(observed.get(i))) {
				o_avg += observed.get(i);
				n++;
			}
		}
		
		o_avg /= n; // (Sum O)/N

		for (Integer i:observed.keySet()) {
			if (isOK(observed.get(i))) {
				sd += Math.pow((observed.get(i) - o_avg), 2); // Sum (O-O_avg)^2
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
