package com.blm.corals.study.window;

import java.util.List;

public interface WindowFunction<E> {

	/**
	 * Accept a list of doubles and perform the function on them.
	 * 
	 */
	public E execute(List<E> windowValues);
}
