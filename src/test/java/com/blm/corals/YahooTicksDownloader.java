package com.blm.corals;

import java.util.concurrent.Callable;

public class YahooTicksDownloader implements Callable<Void> {
	
	private String symbol;

	public YahooTicksDownloader(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public Void call() throws Exception {
	    System.out.println("downloading: " + symbol + " on: " + Thread.currentThread().getId());
	    try {
			Thread.sleep(500);
			System.out.println("DONE: " + symbol + " on: " + Thread.currentThread().getId());			
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}