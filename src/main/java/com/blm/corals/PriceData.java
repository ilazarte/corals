package com.blm.corals;

import java.util.List;

public class PriceData {

	private List<Tick> ticks;
	
	private List<ReadError> errors;

	public PriceData() {
		super();
	}

	public PriceData(List<Tick> ticks, List<ReadError> errors) {
		super();
		this.ticks = ticks;
		this.errors = errors;
	}

	public List<Tick> getTicks() {
		return ticks;
	}

	public void setTicks(List<Tick> ticks) {
		this.ticks = ticks;
	}

	public List<ReadError> getErrors() {
		return errors;
	}

	public void setErrors(List<ReadError> errors) {
		this.errors = errors;
	}

	public boolean hasTicks() {
		return ticks != null && ticks.size() > 0;
	}
	
	public boolean hasErrors() {
		return errors != null && errors.size() > 0;
	}

	@Override
	public String toString() {
		return "PriceData [ticks=" + ticks + ", errors=" + errors + "]";
	}
}
