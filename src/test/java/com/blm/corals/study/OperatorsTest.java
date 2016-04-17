package com.blm.corals.study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.blm.corals.Tick;

public class OperatorsTest {
	
	private Operators<Double> o = Operators.doubles();
	
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

	@Test
	public void nonulls() {
		
		List<Double> d1 = Arrays.asList(new Double[] {
				null, null, 2d, 3d, 4d, null
		});

		Assert.assertEquals(3, o.notnull(d1, TrimType.Both).size());
		Assert.assertEquals(4, o.notnull(d1, TrimType.Leading).size());
		Assert.assertEquals(5, o.notnull(d1, TrimType.Trailing).size());
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
