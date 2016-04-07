package com.blm.corals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

import com.blm.corals.provider.YahooPriceURL;
import com.blm.corals.provider.YahooTickReader;

public class TickReaderTest {

	private YahooTickReader yahooTickReader = new YahooTickReader();

	@Test
	public void readDaily() {

		TestLoader tl = new TestLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-daily.cache");
		List<String> lines = tl.file(fileurl);
		
		PriceData priceData = yahooTickReader.daily(lines);
		List<Tick> daily = priceData.getTicks();
		
		Assert.assertNotNull(daily);
		Assert.assertEquals(0, priceData.getErrors().size());
	}
	
	@Test
	public void readDailyLive() {

		TestLoader tl = new TestLoader();
		YahooPriceURL urls = new YahooPriceURL();
		List<String> lines = tl.http(urls.daily("AAPL"));
		
		PriceData priceData = yahooTickReader.daily(lines);
		List<Tick> daily = priceData.getTicks();
		
		Assert.assertNotNull(daily);
		Assert.assertEquals(0, priceData.getErrors().size());
	}
	
	@Test
	public void multithreadedDailyReadTest() throws InterruptedException, ExecutionException {
		
		String[] symbols = TestWatchlists.MAIN;
		List<Callable<Void>> tasks = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(20);

		for (String symbol : symbols) {
			tasks.add(new YahooTicksDownloader(symbol));
		}
		
		List<Future<Void>> all = executor.invokeAll(tasks);
	    System.out.println("All tasks are finished: " + all.size());
	}
	
	@Test
	public void readDailyOrderError() {

		TestLoader tl = new TestLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-daily-order-error.cache");
		List<String> lines = tl.file(fileurl);
		
		PriceData priceData = yahooTickReader.daily(lines);
		List<Tick> daily = priceData.getTicks();
		
		Assert.assertNotNull(daily);
		Assert.assertEquals(1, priceData.getErrors().size());
		Assert.assertEquals(ReadErrorType.DATE_ORDER, priceData.getErrors().get(0).getType());
	}
	
	@Test
	public void readIntraday() {

		TestLoader tl = new TestLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-intraday.cache");
		List<String> lines = tl.file(fileurl);
			
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
