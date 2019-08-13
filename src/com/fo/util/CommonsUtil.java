package com.fo.util;

public class CommonsUtil {
	private CommonsUtil() {
	}
	
	public static byte[] stringToCharArray(String str, int len) {
		byte[] array = new byte[len];
		System.arraycopy(str.getBytes(), 0, array, 0, str.length() <= len ? str.length() : len);
		return array;
	}
	
}
