package com.blm.corals;

import java.util.Calendar;
import java.util.Date;

/**
 * Simple immutabe date operations
 * @author perico
 *
 */
public class DateHelper {

	public Date midnight() {
		return toMidnight(new Date());
	}
	
	public Date toMidnight(Date date) {
		Calendar cal = cal(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public Date calculate(Date date, PeriodType type, int len) {
		Calendar cal = cal(date);
		int field = type.toCalendarField();
		cal.set(field, cal.get(field) + len);
		return cal.getTime();
	}
	
	public Interval makeDateTimes() {
		return makeDateTimes(PeriodType.YEAR, -1);
	}
	
	public Interval makeDateTimes(PeriodType type, int len) {
		Date end = midnight();
		Date start = calculate(end, type, -len);
		return new Interval(start, end);
	}
	
	public int day(Date date) {
		Calendar cal = cal(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public int month(Date date) {
		Calendar cal = cal(date);
		return cal.get(Calendar.MONTH);
	}

	public int year(Date date) {
		Calendar cal = cal(date);
		return cal.get(Calendar.YEAR);
	}
	
	public Date copy(Date date) {
		return new Date(date.getTime());
	}
	
	public boolean before(Date ts1, Date ts2) {
		return ts1.compareTo(ts2) < 0;
	}

	private Calendar cal(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
