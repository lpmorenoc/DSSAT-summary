package org.ciat.gavilan.model;

import java.util.Map;

import org.ciat.gavilan.control.App;

public class ErrorEvaluator {

	public static Integer NO_VALUE = Integer.MIN_VALUE;

	public static double RMSE(Map<Integer, Double> observed, Map<Integer, Double> calculated) {
		double rmse = 0.0;
		double n = 0;

		for (Integer i : observed.keySet()) {
			if (isComparable(observed.get(i)) && isComparable(calculated.get(i))) {
				rmse += Math.pow((observed.get(i) - calculated.get(i)), 2); // Sum (O-P)^2
				n++;
			}
		}

		rmse /= n; // Sum (O-P)^2 / N
		rmse = Math.sqrt(rmse); // SQRT (Sum (O-P)^2 / N)

		// if it's not numeric set NO_VALUE
		if (!Utils.isNumeric(rmse + "")) {
			rmse = NO_VALUE;
		}

		return rmse;
	}

	public static double NSE(Map<Integer, Double> observed, Map<Integer, Double> calculated) {
		double nse = 0.0; // NSE
		double sd = 0.0; // standard deviations
		double o_avg = 0.0; // average of observed
		double n = 0;
		double rmse = RMSE(observed, calculated);
		
		if(rmse==NO_VALUE){
			return NO_VALUE;
		}

		for (Integer i : observed.keySet()) {
			if (isComparable(observed.get(i))) {
				o_avg += observed.get(i);
				n++;
			}
		}

		o_avg /= n; // (Sum O)/N

		for (Integer i : observed.keySet()) {
			if (isComparable(observed.get(i))) {
				sd += Math.pow((observed.get(i) - o_avg), 2); // Sum (O-O_avg)^2
			}
		}
		nse = 1 - Math.pow((rmse / Math.sqrt(sd)), 2);

		if (!Utils.isNumeric(nse + "")) {
			nse = NO_VALUE;
		}
		// log calculation error
		if (nse < 0 && nse != NO_VALUE) {
			App.log.warning("NSE value is under 0, value: "+nse+"; observed:"+observed+"; calculated:"+calculated);
		}
		return nse;
	}

	private static boolean isComparable(Double value) {
		return value != null && value != -99 && value != 0;
	}

}
