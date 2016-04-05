package com.blm.corals;

public class ReadError {

	private int line;
	private ReadErrorType type;
	public ReadError() {
		super();
	}
	/**
	 * Use this constructor when a line number isn't required.
	 * Sets -1.
	 * @param type
	 */
	public ReadError(ReadErrorType type) {
		this(-1, type);
	}
	/**
	 * A specific error on a line number.
	 * @param line
	 * @param type
	 */
	public ReadError(int line, ReadErrorType type) {
		super();
		this.line = line;
		this.type = type;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public ReadErrorType getType() {
		return type;
	}
	public void setType(ReadErrorType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ReadError [line=" + line + ", type=" + type + "]";
	}
}
