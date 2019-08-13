package com.fo.exception;

public class ObjectDeSerializeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6886227612125112970L;

	public ObjectDeSerializeException() {
		super();
	}
	
	public ObjectDeSerializeException(String str) {
		super(str);
	}
	
	public ObjectDeSerializeException(Exception e) {
		super(e);
	}
	
	public ObjectDeSerializeException(String str, Exception e) {
		super(str, e);
	}
	
}
