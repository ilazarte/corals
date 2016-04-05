package com.blm.corals.provider;

import java.util.Date;

import com.blm.corals.DateHelper;
import com.blm.corals.Interval;
import com.blm.corals.PeriodType;

public class YahooPriceURL {
	
	private DateHelper dateHelper = new DateHelper();
	
	public String daily(String symbol) {
		Interval iv = dateHelper.makeDateTimes(PeriodType.YEAR, 1);
		return daily(symbol, iv);
	}
	
	public String daily(String symbol, Interval iv) {
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
	
	public String intraday(String symbol) {
		return String.format("http://chartapi.finance.yahoo.com/instrument/1.0/%s/chartdata;type=quote;range=5d/csv", symbol);
	}
}
