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

	private DateHelper dateHelper = new DateHelper();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Parse the list of lines for the daily/historical format.
	 * @param lines
	 * @return
	 */
	public PriceData daily(List<String> lines) {
		
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
			prevTimestamp = tick.getTimestamp();
			
			try {
				tick.set("high", Double.parseDouble(split[2]));
				tick.set("low", Double.parseDouble(split[3]));
				tick.set("close", Double.parseDouble(split[4]));
				tick.set("volume", Double.parseDouble(split[5]));
				tick.set("adjclose", Double.parseDouble(split[6]));
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
		
		List<Tick> ticks = new ArrayList<Tick>();
		List<ReadError> errors = new ArrayList<ReadError>();
		Date prevTimestamp = null;
		int lineNum = 0;
		
		boolean start = false;

		for (String line : lines) {
			
			boolean lineError = false;
			
			String[] split = line.split(",");
			
			if (start) {
				Tick tick = new Tick();
				
				Date date = new Date();
				try {
					date.setTime(Long.parseLong(split[0] + "000"));
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
				prevTimestamp = tick.getTimestamp();
				
				try {
					tick.set("close", Double.parseDouble(split[1]));
					tick.set("high", Double.parseDouble(split[2]));
					tick.set("low", Double.parseDouble(split[3]));
					tick.set("open", Double.parseDouble(split[4]));
					tick.set("volume", Double.parseDouble(split[5]));
					
				} catch (RuntimeException e) {
					errors.add(new ReadError(lineNum, ReadErrorType.PRICE_PARSE));
					lineError = true;
				}
				
				if (!lineError) {
					ticks.add(tick);
				}
			
				lineNum++;
		
			} else if (line.startsWith("volume")) {
				start = true;
			}			
		}

		return new PriceData(ticks, errors);
	}
}
