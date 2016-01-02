package com.blm.corals;

import java.util.Calendar;

public enum PeriodType {
	
	DAY, WEEK, MONTH, YEAR;

	int toCalendarField() {
		switch (this) {
		case DAY:
			return Calendar.DAY_OF_YEAR;
		case WEEK:
			return Calendar.WEEK_OF_YEAR;
		case MONTH:
			return Calendar.MONTH;
		case YEAR:
			return Calendar.YEAR;
		default:
			throw new IllegalArgumentException("Unmapped period type.");
		}
	}
}
