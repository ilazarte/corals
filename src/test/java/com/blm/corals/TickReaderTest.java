package com.blm.corals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.blm.corals.loader.FileURLLoader;

public class TickReaderTest {

	@Test
	public void readHistorical() {

		FileURLLoader ful = new FileURLLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-daily.cache");
		List<String> lines = ful.load(fileurl);
		
		YahooTickReader yahooTickReader = new YahooTickReader();
		List<Tick> historical = yahooTickReader.historical(lines);
		
		Assert.assertNotNull(historical);
	}
	
	@Test
	public void readIntraday() {

		FileURLLoader ful = new FileURLLoader();
		String fileurl = asFileURL("src/test/resources/yahoo-intraday.cache");
		List<String> lines = ful.load(fileurl);
			
		YahooTickReader yahooTickReader = new YahooTickReader();
		List<Tick> intraday = yahooTickReader.intraday(lines);
		
		Assert.assertNotNull(intraday);
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
