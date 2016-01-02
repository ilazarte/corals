package com.blm.corals.study;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.blm.corals.Tick;
import com.blm.corals.YahooPrices;
import com.blm.corals.loader.HTTPURLLoader;
import com.blm.corals.loader.MemoryURLLoader;
import com.blm.corals.loader.URLLoader;
import com.blm.corals.study.window.Average;

public class StudyTest {

	/**
	 * TODO assert values for correctness within sigma
	 */
	@Test
	public void percentChangeTest() {

		URLLoader ul = new MemoryURLLoader(new HTTPURLLoader());
		YahooPrices yp = new YahooPrices(ul);
		Operators o = new Operators();

		List<Tick> prices = yp.historical("GOOG");
		List<Double> fullval = o.get(prices, "close");
		
		List<Double> val = o.last(fullval, 5);
		List<Double> valold = o.shift(val, 1);
		List<Double> diff = o.subtract(val, valold);
		List<Double> div = o.divide(diff, valold);
		List<Double> pct = o.multiply(div, 100.0d);
		
		System.out.println(val);
		System.out.println(pct);
		
		
		Assert.assertEquals(val.size(), pct.size());
	}
	
	/**
	 * TODO assert values for correctness within sigma
	 */
	@Test
	public void simpleMovingAvgTest() {

		URLLoader ul = new MemoryURLLoader(new HTTPURLLoader());
		YahooPrices yp = new YahooPrices(ul);
		Operators o = new Operators();

		List<Tick> prices = yp.historical("GOOG");
		List<Double> fullval = o.get(prices, "close");
		
		List<Double> val = o.last(fullval, 8);
		List<Double> avgs = o.window(val, 5, new Average());
		List<Double> div = o.divide(val, avgs);
		
		System.out.println(val);
		System.out.println(div);
				
		Assert.assertEquals(val.size(), div.size());
	}
}
