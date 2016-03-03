package slogo.model;

import generic.Pair;

public class SlogoException extends RuntimeException {
	
	private Pair<String, String> error;

	public SlogoException() {
	}
	
	public SlogoException(String message) {
		super(message);
	}
	
	public SlogoException(Pair<String, String> error) {
		this.error = error;
	}
	
	public Pair<String, String> getException() {
		return this.error;
	}
	
	public SlogoException(Throwable cause) {
		super(cause);
	}
	
	public SlogoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SlogoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
