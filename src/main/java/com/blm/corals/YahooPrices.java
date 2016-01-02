package com.blm.corals;

import java.util.Date;
import java.util.List;

import com.blm.corals.loader.URLLoader;

public class YahooPrices {

	private URLLoader loader;
	
	private DateHelper dateHelper = new DateHelper();
	
	private YahooTickReader reader = new YahooTickReader();
	
	public YahooPrices(URLLoader loader) {
		super();
		this.loader = loader;
	}
	
	/**
	 * Read the prices from the historical data source.
	 * The prices are returned in order of date. 
	 * @param symbol
	 * @return
	 */
	public List<Tick> historical(String symbol) {
		
		String url = this.makeDownloadHistorialUrl(symbol);
		List<String> lines = loader.load(url);
		List<Tick> prices = reader.historical(lines);
		
		return prices;
	}
	
	/**
	 * Download the intraday values as a list of ticks.
	 * @param symbol
	 * @return
	 */
	public List<Tick> intraday(String symbol) {
		
		String url = this.makeDownloadIntradayUrl(symbol);
		List<String> lines = loader.load(url);
		List<Tick> prices = reader.intraday(lines);
		
		return prices;
	}
	
	private String makeDownloadHistorialUrl (String symbol) {
		Interval iv = dateHelper.makeDateTimes(PeriodType.YEAR, 1);
		return makeDownloadHistoricalUrl(symbol, iv);
	}
	
	private String makeDownloadHistoricalUrl (String symbol, Interval iv) {
		Date start = iv.getBegin();
		Date end = iv.getEnd();
		String url = String.format("http://ichart.finance.yahoo.com/table.csv?" +
		      "s=%s&" +
		      "a=%s&b=%s&c=%s&" +
		      "d=%s&e=%s&f=%s&" +
		      "g=d&ignore=.csv",
		      symbol,
		      dateHelper.month(start), dateHelper.day(start), dateHelper.year(start),
		      dateHelper.month(end), dateHelper.day(end), dateHelper.year(end));
		return url;
	}
	
	private String makeDownloadIntradayUrl(String symbol) {
		return String.format("http://chartapi.finance.yahoo.com/instrument/1.0/%s/chartdata;type=quote;range=5d/csv", symbol);
	}
}
