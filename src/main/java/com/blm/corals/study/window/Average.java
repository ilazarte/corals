package com.blm.corals.study.window;

import java.util.List;

public class Average implements WindowFunction {

	@Override
	public Double execute(List<Double> windowValues) {
		double sum = 0.0d;
		for (Double d : windowValues) {
			sum += d;
		}
		return sum / windowValues.size();
	}

}
