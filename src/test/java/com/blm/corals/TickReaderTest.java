package com.blm.corals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.blm.corals.provider.YahooTickReader;

public class TickReaderTest {

	@Test
	public void readDaily() {

		TestLoader tl = new TestLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-daily.cache");
		List<String> lines = tl.file(fileurl);
		
		YahooTickReader yahooTickReader = new YahooTickReader();
		PriceData priceData = yahooTickReader.daily(lines);
		List<Tick> historical = priceData.getTicks();
		
		Assert.assertNotNull(historical);
		Assert.assertEquals(0, priceData.getErrors().size());
	}
	
	@Test
	public void readDailyOrderError() {

		TestLoader tl = new TestLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-daily-order-error.cache");
		List<String> lines = tl.file(fileurl);
		
		YahooTickReader yahooTickReader = new YahooTickReader();
		PriceData priceData = yahooTickReader.daily(lines);
		List<Tick> historical = priceData.getTicks();
		
		Assert.assertNotNull(historical);
		Assert.assertEquals(1, priceData.getErrors().size());
		Assert.assertEquals(ReadErrorType.DATE_ORDER, priceData.getErrors().get(0).getType());
	}
	
	@Test
	public void readIntraday() {

		TestLoader tl = new TestLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-intraday.cache");
		List<String> lines = tl.file(fileurl);
			
		YahooTickReader yahooTickReader = new YahooTickReader();
		PriceData priceData = yahooTickReader.intraday(lines);
		List<Tick> intraday = priceData.getTicks();
		
		Assert.assertNotNull(intraday);
		Assert.assertEquals(0, priceData.getErrors().size());
	}
	
	private String asFileURL(String path) {
		String parent = new File(".").getAbsolutePath();
		URI uri = new File(parent, path).toURI();
		URL url;
		try {
			url = uri.toURL();
			String fileurl = url.toString();
			return fileurl;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
