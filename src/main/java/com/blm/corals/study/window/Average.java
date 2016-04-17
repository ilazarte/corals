package com.blm.corals.study.window;

import java.util.List;

/**
 * TODO Migrate this to Operators or study package?  rename to DoubleAverage? Extra package seems unnecessary.  
 */
public class Average implements WindowFunction<Double> {

	@Override
	public Double execute(List<Double> windowValues) {
		double sum = 0.0d;
		for (Double d : windowValues) {
			sum += d;
		}
		return sum / windowValues.size();
	}
}
