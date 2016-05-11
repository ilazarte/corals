package com.blm.corals.study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.blm.corals.Tick;
import com.blm.corals.study.window.WindowFunction;

public class Operators<E> {
	
	private Ops<E> op;
	
	private Operators(Ops<E> op) {
		this.op = op;
	}
	
	public static Operators<Double> doubles() {
		return new Operators<Double>(new DoubleOps());
	}
	
	public static Operators<Long> longs() {
		return new Operators<Long>(new LongOps());
	}
	
	public static Operators<Float> floats() {
		return new Operators<Float>(new FloatOps());
	}
	
	public static Operators<Integer> integers() {
		return new Operators<Integer>(new IntegerOps());
	}
	
	private static interface Ops<E> {
		public E execute(Operation o, E e1, E e2);
	}
	
	private static class DoubleOps implements Ops<Double> {
		@Override
		public Double execute(Operation operation, Double e1, Double e2) {
			switch (operation) {
			case ADD:
				return e1 + e2;
			case SUB:
				return e1 - e2;
			case MUL:
				return e1 * e2;
			case DIV:
				return e1 / e2;
			default:
				throw new RuntimeException("Unreachable case");
			}
		}
	}
	
	private static class LongOps implements Ops<Long> {
		@Override
		public Long execute(Operation operation, Long e1, Long e2) {
			switch (operation) {
			case ADD:
				return e1 + e2;
			case SUB:
				return e1 - e2;
			case MUL:
				return e1 * e2;
			case DIV:
				return e1 / e2;
			default:
				throw new RuntimeException("Unreachable case");
			}
		}
	}
	
	private static class IntegerOps implements Ops<Integer> {
		@Override
		public Integer execute(Operation operation, Integer e1, Integer e2) {
			switch (operation) {
			case ADD:
				return e1 + e2;
			case SUB:
				return e1 - e2;
			case MUL:
				return e1 * e2;
			case DIV:
				return e1 / e2;
			default:
				throw new RuntimeException("Unreachable case");
			}
		}
	}
	
	private static class FloatOps implements Ops<Float> {
		@Override
		public Float execute(Operation operation, Float e1, Float e2) {
			switch (operation) {
			case ADD:
				return e1 + e2;
			case SUB:
				return e1 - e2;
			case MUL:
				return e1 * e2;
			case DIV:
				return e1 / e2;
			default:
				throw new RuntimeException("Unreachable case");
			}
		}
	}
	
	private static enum Operation {
		ADD, SUB, MUL, DIV;
	};
	
	public List<E> add(List<E> ds, E d) {
		return listscalar(ds, d, Operation.ADD);
	}
	
	public List<E> add(E d, List<E> ds) {
		return scalarlist(d, ds, Operation.ADD);
	}
	
	public List<E> add(List<E> ds1, List<E> ds2) {
		return listlist(ds1, ds2, Operation.ADD);
	}
	
	public List<E> subtract(List<E> ds, E d) {
		return listscalar(ds, d, Operation.SUB);
	}
	
	public List<E> subtract(E d, List<E> ds) {
		return scalarlist(d, ds, Operation.SUB);
	}
	
	public List<E> subtract(List<E> ds1, List<E> ds2) {
		return listlist(ds1, ds2, Operation.SUB);
	}
	
	public List<E> multiply(List<E> ds, E d) {
		return listscalar(ds, d, Operation.MUL);
	}
	
	public List<E> multiply(E d, List<E> ds) {
		return scalarlist(d, ds, Operation.MUL);
	}
	
	public List<E> multiply(List<E> ds1, List<E> ds2) {
		return listlist(ds1, ds2, Operation.MUL);
	}
	
	public List<E> divide(List<E> ds, E d) {
		return listscalar(ds, d, Operation.DIV);
	}
	
	public List<E> divide(E d, List<E> ds) {
		return scalarlist(d, ds, Operation.DIV);
	}
	
	public List<E> divide(List<E> ds1, List<E> ds2) {
		return listlist(ds1, ds2, Operation.DIV);
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

	/**
	 * Move the values across the list, padding nulls to the front.
	 * @param ds
	 * @param len
	 * @return
	 */
	public <T> List<T> shift(List<T> ds, int len) {
		if (len < 1) {
			throw new IllegalArgumentException("Invalid shift specified");
		}
		List<T> res = new ArrayList<T>();
		for (int i = 0; i < len; i++) {
			res.add(null);
		}
		for (int i = 0, max = ds.size() - len; i < max; i++) {
			res.add(ds.get(i));
		}
		return res;
	}

	/*
	 * BEGIN: General purpose list functions.
	 */
	
	/**
	 * From a starting index, return values.
	 * If the starting index exceeds the list size, an empty list is returned.
	 * @param ds
	 * @param start
	 * @return
	 */
	public <T> List<T> range(List<T> ds, int start) {
		List<T> res = new ArrayList<T>();
		for (int i = 0; i < ds.size(); i++) {
			if (i >= start) {
				res.add(ds.get(i));
			}
		}
		return res;
	}

	/**
	 * First num values from the list.
	 * If the num exceeds the list length, length is used instead.
	 * @param ds
	 * @param num
	 * @return
	 */
	public <T> List<T> first(List<T> ds, int num) {
		List<T> res = new ArrayList<T>();
		if (num > ds.size()) {
			num = ds.size();
		}
		for (int i = 0; i < num; i++) {
			res.add(ds.get(i));
		}
		return res;
	}
	
	/**
	 * Last num values from the list.
	 * If the num is longer than list values, starting index of 0 is used.
	 * @param ds
	 * @param num
	 * @return
	 */
	public <T> List<T> last(List<T> ds, int num) {
		List<T> res = new ArrayList<T>();
		int start = ds.size() - num;
		if (start < 0) {
			start = 0;
		}
		for (int i = start; i < ds.size(); i++) {
			res.add(ds.get(i));
		}
		return res;
	}
	
	/**
	 * Trim all nulls from the collections based on trim type.
	 * @param ds
	 * @param type
	 * @return
	 */
	public <T> List<T> notnull(List<T> ds, TrimType type) {
		
		List<T> trimmed = new ArrayList<T>(ds);
		if (TrimType.Leading.equals(type) ||
				TrimType.Both.equals(type)) {
			Iterator<T> it = trimmed.iterator();
			while (it.hasNext()) {
				T next = it.next();
				if (next == null) {
					it.remove();
				} else {
					break;
				}
			}
		}
		if (TrimType.Trailing.equals(type) ||
			TrimType.Both.equals(type)) {
			Collections.reverse(trimmed);
			Iterator<T> it = trimmed.iterator();
			while (it.hasNext()) {
				T next = it.next();
				if (next == null) {
					it.remove();
				} else {
					break;
				}
			}
			Collections.reverse(trimmed);
		}
			
		
		return trimmed;
	}
	
	private List<E> listscalar(List<E> ds, E d, Operation o) {		
		List<E> res = new ArrayList<E>();
		for (int i = 0, len = ds.size(); i < len; i++) {
			E v = ds.get(i);
			if (v == null || d == null) {
				res.add(null);
			} else {
				res.add(op.execute(o, v, d));				
			}
		}		
		return res;
	}

	private List<E> scalarlist(E d, List<E> ds, Operation o) {		
		List<E> res = new ArrayList<E>();
		for (int i = 0, len = ds.size(); i < len; i++) {
			E v = ds.get(i);
			if (v == null || d == null) {
				res.add(null);
			} else {
				res.add(op.execute(o, v, d));				
			}
		}
		return res;
	}

	private List<E> listlist(List<E> ds1, List<E> ds2, Operation o) {
		int len = Math.max(ds1.size(), ds2.size());
		List<E> res = new ArrayList<E>();
		
		for (int i = 0; i < len; i++) {
			E v1 = i < ds1.size() ? ds1.get(i) : null;
			E v2 = i < ds2.size() ? ds2.get(i) : null;
			if (v1 == null || v2 == null) {
				res.add(null);
			} else {
				res.add(op.execute(o, v1, v2));
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
	public List<E> window(List<E> values, int length, WindowFunction<E> wf) {
		
		List<E> res = new ArrayList<E>();
		if (values.size() < length) {
			for (int i = 0; i < values.size(); i++) {
				res.add(null);
			}
			return res;
		}
		
		List<E> wind = new ArrayList<E>();
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
