package com.blm.corals;

import java.util.Date;

public class Interval {

	private Date begin;
	
	private Date end;

	public Interval(Date begin, Date end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Interval [begin=" + begin + ", end=" + end + "]";
	}
}
