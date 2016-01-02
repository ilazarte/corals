package com.blm.corals.study;

import java.util.ArrayList;
import java.util.List;

import com.blm.corals.Tick;
import com.blm.corals.study.window.WindowFunction;

public class Operators {
	
	private static enum Operation {
		
		ADD, SUB, MUL, DIV;
		
		Double execute(Double d1, Double d2) {
			switch (this) {
			case ADD:
				return d1 + d2;
			case SUB:
				return d1 - d2;
			case MUL:
				return d1 * d2;
			case DIV:
				return d1 / d2;
			default:
				throw new RuntimeException("Unreachable case");
			}
		}
	};
	
	public List<Double> add(List<Double> ds, Double d) {
		return listscalar(ds, d, Operation.ADD);
	}
	
	public List<Double> add(Double d, List<Double> ds) {
		return scalarlist(d, ds, Operation.ADD);
	}
	
	public List<Double> add(List<Double> ds1, List<Double> ds2) {
		return listlist(ds1, ds2, Operation.ADD);
	}
	
	public List<Double> subtract(List<Double> ds, Double d) {
		return listscalar(ds, d, Operation.SUB);
	}
	
	public List<Double> subtract(Double d, List<Double> ds) {
		return scalarlist(d, ds, Operation.SUB);
	}
	
	public List<Double> subtract(List<Double> ds1, List<Double> ds2) {
		return listlist(ds1, ds2, Operation.SUB);
	}
	
	public List<Double> multiply(List<Double> ds, Double d) {
		return listscalar(ds, d, Operation.MUL);
	}
	
	public List<Double> multiply(Double d, List<Double> ds) {
		return scalarlist(d, ds, Operation.MUL);
	}
	
	public List<Double> multiply(List<Double> ds1, List<Double> ds2) {
		return listlist(ds1, ds2, Operation.MUL);
	}
	
	public List<Double> divide(List<Double> ds, Double d) {
		return listscalar(ds, d, Operation.DIV);
	}
	
	public List<Double> divide(Double d, List<Double> ds) {
		return scalarlist(d, ds, Operation.DIV);
	}
	
	public List<Double> divide(List<Double> ds1, List<Double> ds2) {
		return listlist(ds1, ds2, Operation.DIV);
	}
	
	public List<Double> shift(List<Double> ds, int len) {
		if (len < 1) {
			throw new IllegalArgumentException("Invalid shift specified");
		}
		List<Double> res = new ArrayList<Double>();
		for (int i = 0; i < len; i++) {
			res.add(null);
		}
		for (int i = 0, max = ds.size() - len; i < max; i++) {
			res.add(ds.get(i));
		}
		return res;
	}
	
	/**
	 * Retrieve the key as array.
	 * @param ticks
	 * @param key
	 * @return
	 */
	public List<Double> get(List<Tick> ticks, String key) {
		List<Double> res = new ArrayList<Double>();
		for(Tick tick : ticks) {
			res.add(tick.get(key));
		}		
		return res;
	}
	
	/**
	 * Set the array to key.
	 * @param ticks
	 * @param ds
	 * @param key
	 */
	public void set(List<Tick> ticks, List<Double> ds, String key) {
		if (ticks.size() != ds.size()) {
			String msg = String.format("ticks (%s) to list(%s) size does not match%n", ticks.size(), ds.size());
			throw new RuntimeException(msg);
		}
	}

	public List<Double> range(List<Double> ds, int start) {
		List<Double> res = new ArrayList<Double>();
		for (int i = 0; i < ds.size(); i++) {
			if (i >= start) {
				res.add(ds.get(i));
			}
		}
		return res;
	}

	public List<Double> first(List<Double> ds, int num) {
		List<Double> res = new ArrayList<Double>();
		for (int i = 0; i < num; i++) {
			res.add(ds.get(i));
		}
		return res;
	}
	
	public List<Double> last(List<Double> ds, int num) {
		List<Double> res = new ArrayList<Double>();
		int start = ds.size() - num;
		for (int i = start; i < ds.size(); i++) {
			res.add(ds.get(i));
		}
		return res;
	}
	
	private List<Double> listscalar(List<Double> ds, Double d, Operation o) {		
		List<Double> res = new ArrayList<Double>();
		for (int i = 0, len = ds.size(); i < len; i++) {
			Double v = ds.get(i);
			if (v == null || d == null) {
				res.add(null);
			} else {
				res.add(o.execute(v, d));				
			}
		}		
		return res;
	}

	private List<Double> scalarlist(Double d, List<Double> ds, Operation o) {		
		List<Double> res = new ArrayList<Double>();
		for (int i = 0, len = ds.size(); i < len; i++) {
			Double v = ds.get(i);
			if (v == null || d == null) {
				res.add(null);
			} else {
				res.add(o.execute(v, d));				
			}
		}
		return res;
	}

	private List<Double> listlist(List<Double> ds1, List<Double> ds2, Operation o) {
		int len = Math.max(ds1.size(), ds2.size());
		List<Double> res = new ArrayList<Double>();
		
		for (int i = 0; i < len; i++) {
			Double v1 = i < ds1.size() ? ds1.get(i) : null;
			Double v2 = i < ds2.size() ? ds2.get(i) : null;
			if (v1 == null || v2 == null) {
				res.add(null);
			} else {
				res.add(o.execute(v1, v2));
			}
		}
		
		return res;
	}

	/**
	 * Run a function against a window of n size.
	 * @param values
	 * @param length
	 * @param wf
	 * @return
	 */
	public List<Double> window(List<Double> values, int length, WindowFunction wf) {
		
		List<Double> res = new ArrayList<Double>();
		if (values.size() < length) {
			for (int i = 0; i < values.size(); i++) {
				res.add(null);
			}
			return res;
		}
		
		List<Double> wind = new ArrayList<Double>();
		for (int i = 0, valueLength = values.size(); i < valueLength; i++) {
			if (wind.size() < length) {
				wind.add(values.get(i));
				res.add(null);
			} else {
				res.add(wf.execute(wind));
				wind.remove(0);
				wind.add(values.get(i));
			}
		}
		
		return res;
	}
}
