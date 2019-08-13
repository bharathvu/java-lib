package com.fo.exception;

public class ObjectSerializeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8470731914958348148L;

	public ObjectSerializeException() {
		super();
	}
	
	public ObjectSerializeException(String str) {
		super(str);
	}
	
	public ObjectSerializeException(Exception e) {
		super(e);
	}
	
	public ObjectSerializeException(String str, Exception e) {
		super(str, e);
	}
	
}
