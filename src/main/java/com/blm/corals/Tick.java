package com.blm.corals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tick {

	private Date timestamp;
	
	private Map<String, Double> values = new HashMap<String, Double>();
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Double get(String key) {
		return values.get(key);
	}
	
	public void set(String key, Double value) {
		values.put(key, value);
	}
	
	public List<String> keys() {
		return new ArrayList<String>(values.keySet());
	}

	@Override
	public String toString() {
		return "Tick [timestamp=" + timestamp + ", values=" + values + "]";
	}
}
