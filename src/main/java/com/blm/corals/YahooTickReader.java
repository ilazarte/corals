package com.blm.corals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class YahooTickReader {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Parse the list of lines for the daily/historical format.
	 * @param lines
	 * @return
	 */
	public List<Tick> historical(List<String> lines) {
		
		List<Tick> ticks = new ArrayList<Tick>();

		for (int i = 1, max = lines.size(); i < max; i++) {
			String line = lines.get(i);
			String[] split = line.split(",");
			
			Tick tick = new Tick();
			try {
				
				tick.setTimestamp(sdf.parse(split[0]));
				tick.set("open", Double.parseDouble(split[1]));
				tick.set("high", Double.parseDouble(split[2]));
				tick.set("low", Double.parseDouble(split[3]));
				tick.set("close", Double.parseDouble(split[4]));
				tick.set("volume", Double.parseDouble(split[5]));
				tick.set("adjclose", Double.parseDouble(split[6]));
				
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			ticks.add(tick);
		}
		
		Collections.reverse(ticks);
		
		return ticks;
	}
	
	/**
	 * Parse a list of lines via the intraday format.
	 * @param lines
	 * @return
	 */
	public List<Tick> intraday(List<String> lines) {
		
		List<Tick> ticks = new ArrayList<Tick>();
		
		boolean start = false;
		for (String line : lines) {
			
			String[] split = line.split(",");
			
			if (start) {
				Date date = new Date();
				date.setTime(Long.parseLong(split[0] + "000"));
				
				Tick tick = new Tick();
				tick.setTimestamp(date);
				tick.set("close", Double.parseDouble(split[1]));
				tick.set("high", Double.parseDouble(split[2]));
				tick.set("low", Double.parseDouble(split[3]));
				tick.set("open", Double.parseDouble(split[4]));
				tick.set("volume", Double.parseDouble(split[5]));
				ticks.add(tick);
				
			} else if (line.startsWith("volume")) {
				start = true;
			}
		}
		
		Collections.reverse(ticks);
		
		return ticks;
	}
}
