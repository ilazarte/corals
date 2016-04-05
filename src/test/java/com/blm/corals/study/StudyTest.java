package com.blm.corals.study;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.blm.corals.TestLoader;
import com.blm.corals.Tick;
import com.blm.corals.provider.YahooPriceURL;
import com.blm.corals.provider.YahooTickReader;
import com.blm.corals.study.window.Average;

public class StudyTest {

	/**
	 * TODO assert values for correctness within sigma
	 */
	@Test
	public void percentChangeTest() {

		Operators o = new Operators();

		List<Tick> prices = daily("GOOG");
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
	 * TODO assert values for correctness within sigma.
	 * After an average operation,
	 *  don't forget to remove everything but the first N of the average length.
	 *  
	 *  They will be null.
	 */
	@Test
	public void simpleMovingAvgTest() {

		Operators o = new Operators();

		List<Tick> prices = daily("GOOG");
		List<Double> fullval = o.get(prices, "close");
		
		List<Double> val = o.last(fullval, 8);
		List<Double> avgs = o.window(val, 5, new Average());
		List<Double> div = o.divide(val, avgs);
		List<Double> ranged = o.range(div, 5);
		
		System.out.println(val);
		System.out.println(div);
		System.out.println(ranged);
				
		Assert.assertEquals(val.size(), div.size());
	}

	private List<Tick> daily(String symbol) {
		TestLoader ul = new TestLoader();
		YahooPriceURL urls = new YahooPriceURL();
		YahooTickReader reader = new YahooTickReader();
		
		String url = urls.daily(symbol);
		List<String> lines = ul.http(url);
		List<Tick> ticks = reader.daily(lines).getTicks();
		return ticks;
	}
}
