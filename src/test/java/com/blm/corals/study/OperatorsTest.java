package com.blm.corals.study;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.blm.corals.Tick;

public class OperatorsTest {
	
	private Operators o = new Operators();
	
	@Test
	public void shift() {
		List<Tick> ticks = ticks();
		List<Double> close = o.get(ticks, "close");
		List<Double> shifted1 = o.shift(close, 1);
		List<Double> shifted2 = o.shift(close, 2);
		
		System.out.println(shifted1);
		System.out.println(shifted2);
	}
	
	/**
	 * Divide operation
	 */
	@Test
	public void divide() {
		List<Tick> ticks = ticks();
		List<Double> close = o.get(ticks, "close");
		List<Double> div = o.divide(close, 10d);
		
		System.out.println(div);
	}
	
	public List<Tick> ticks() {
		List<Tick> ticks = new ArrayList<Tick>();
		
		ticks.add(tick("close", 20.d));
		ticks.add(tick("close", 30.d));
		ticks.add(tick("close", 40.d));
		ticks.add(tick("close", 50.d));
		
		return ticks;
	}

	private Tick tick(String key, Double d) {
		Tick tick = new Tick();
		tick.set(key, d);
		return tick;
	}
}
