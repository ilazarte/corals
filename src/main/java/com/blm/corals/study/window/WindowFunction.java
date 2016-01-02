package com.blm.corals.study.window;

import java.util.List;

public interface WindowFunction {

	/**
	 * Accept a list of doubles and perform the function on them.
	 * 
	 */
	public Double execute(List<Double> windowValues);
}
