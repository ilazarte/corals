package com.blm.corals.provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.blm.corals.DateHelper;
import com.blm.corals.PriceData;
import com.blm.corals.ReadError;
import com.blm.corals.ReadErrorType;
import com.blm.corals.Tick;

public class YahooTickReader {

	private static final String STR_000_PAD = "000";
	private static final String STR_ADJCLOSE = "adjclose";
	private static final String STR_VOLUME = "volume";
	private static final String STR_OPEN = "open";
	private static final String STR_LOW = "low";
	private static final String STR_HIGH = "high";
	private static final String STR_CLOSE = "close";
	private static final String DATE_FORMAT_STR = "yyyy-MM-dd";
	
	
	/**
	 * Parse the list of lines for the daily/historical format.
	 * @param lines
	 * @return
	 */
	public PriceData daily(List<String> lines) {
		DateHelper dateHelper = new DateHelper();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
		List<Tick> ticks = new ArrayList<Tick>();
		List<ReadError> errors = new ArrayList<ReadError>();
		Date prevTimestamp = null;		
		
		if (lines == null || lines.size() == 0) {
			errors.add(new ReadError(ReadErrorType.NO_DATA));
			return new PriceData(ticks, errors);
		}

		for (int lineNum = 1, max = lines.size(); lineNum < max; lineNum++) {
			
			boolean lineError = false;
			String line = lines.get(lineNum);
			String[] split = line.split(",");
			
			Tick tick = new Tick();
			try {
				tick.setTimestamp(sdf.parse(split[0]));
			} catch (ParseException e) {
				errors.add(new ReadError(lineNum, ReadErrorType.DATE_FORMAT));
				lineError = true;
			}
			
			if (prevTimestamp != null && tick.getTimestamp() != null) {
				if (!dateHelper.before(tick.getTimestamp(), prevTimestamp)) {
					errors.add(new ReadError(lineNum, ReadErrorType.DATE_ORDER));
					lineError = true;
				}
			}
			prevTimestamp = dateHelper.copy(tick.getTimestamp());
			
			try {
				tick.set(STR_OPEN, Double.parseDouble(split[1]));
				tick.set(STR_HIGH, Double.parseDouble(split[2]));
				tick.set(STR_LOW, Double.parseDouble(split[3]));
				tick.set(STR_CLOSE, Double.parseDouble(split[4]));
				tick.set(STR_VOLUME, Double.parseDouble(split[5]));
				tick.set(STR_ADJCLOSE, Double.parseDouble(split[6]));
			} catch (RuntimeException e) {
				errors.add(new ReadError(lineNum, ReadErrorType.PRICE_PARSE));
				lineError = true;
			}
			
			if (!lineError) {
				ticks.add(tick);	
			}			
		}
		
		Collections.reverse(ticks);

		return new PriceData(ticks, errors);
	}
	
	/**
	 * Parse a list of lines via the intraday format.
	 * @param lines
	 * @return
	 */
	public PriceData intraday(List<String> lines) {
		
		DateHelper dateHelper = new DateHelper();
		List<Tick> ticks = new ArrayList<Tick>();
		List<ReadError> errors = new ArrayList<ReadError>();
		Date prevTimestamp = null;
		int lineNum = 0;
		
		if (lines == null || lines.size() == 0) {
			errors.add(new ReadError(ReadErrorType.NO_DATA));
			return new PriceData(ticks, errors);
		}
		
		boolean start = false;

		for (String line : lines) {
			
			boolean lineError = false;
			
			String[] split = line.split(",");
			
			if (start) {
				Tick tick = new Tick();
				
				Date date = new Date();
				try {
					date.setTime(Long.parseLong(split[0] + STR_000_PAD));
					tick.setTimestamp(date);
				} catch (RuntimeException e) {
					errors.add(new ReadError(lineNum, ReadErrorType.DATE_FORMAT));
					lineError = true;
				}
				
				if (prevTimestamp != null && tick.getTimestamp() != null) {
					if (!dateHelper.before(prevTimestamp, tick.getTimestamp())) {
						errors.add(new ReadError(lineNum, ReadErrorType.DATE_ORDER));
						lineError = true;
					}
				}
				prevTimestamp = dateHelper.copy(tick.getTimestamp());
				
				try {
					tick.set(STR_CLOSE, Double.parseDouble(split[1]));
					tick.set(STR_HIGH, Double.parseDouble(split[2]));
					tick.set(STR_LOW, Double.parseDouble(split[3]));
					tick.set(STR_OPEN, Double.parseDouble(split[4]));
					tick.set(STR_VOLUME, Double.parseDouble(split[5]));
					
				} catch (RuntimeException e) {
					errors.add(new ReadError(lineNum, ReadErrorType.PRICE_PARSE));
					lineError = true;
				}
				
				if (!lineError) {
					ticks.add(tick);
				}
			
				lineNum++;
		
			} else if (line.startsWith(STR_VOLUME)) {
				start = true;
			}			
		}

		return new PriceData(ticks, errors);
	}
}
