package com.fo.build;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fo.annotation.FieldOrder;
import com.fo.annotation.SelObject;
import com.fo.exception.ObjectSerializeException;

public class SerializationCommons {
	boolean verifyClass(Class<?> cls) {
		for (Annotation an : cls.getAnnotations()) {
			if (an.annotationType() == SelObject.class) {
				return true;
			}
		}
		return false;
	}

	boolean verifyMapOrder(Map<Integer, Field> map) {
		List<Integer> list = new ArrayList<Integer>(map.keySet());
		Collections.sort(list);

		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i + 1) - list.get(i) > 1)
				return false;
		}

		return true;
	}

	void buildFieldsMap(Class<?> cls, Map<Integer, Field> map) throws ObjectSerializeException {
		for (Field f : cls.getDeclaredFields()) {
			if (!verifyField(f) || f.isSynthetic())
				continue;

			if (map.containsKey(getAnnotation(f).value())) {
				throw new ObjectSerializeException("duplicate order value for field " + f.getName());
			}

			map.put(getAnnotation(f).value(), f);
		}
	}

	boolean verifyField(Field f) throws ObjectSerializeException {
		if (!checkFieldType(f))
			throw new ObjectSerializeException("unsupported field type " + f.getType());

		if (getAnnotation(f) == null)
			throw new ObjectSerializeException("no annotation found for " + f.getType());

		if (getAnnotation(f).value() <= 0)
			throw new ObjectSerializeException("order value should be greater than 0 for field " + f.getName());

		if (getAnnotation(f).length() <= 0 && f.getType() == String.class)
			throw new ObjectSerializeException("Field type String must contain a proper length ");

		return true;
	}

	boolean checkFieldType(Field f) {
		Set<Class<?>> list = new HashSet<Class<?>>(Arrays.<Class<?>>asList(byte.class, short.class, int.class,
				long.class, float.class, double.class, char.class, String.class, Byte.class, Short.class, Integer.class,
				Long.class, Float.class, Double.class, Character.class));
		return list.contains(f.getType()) || list.contains(f.getType().getComponentType());
	}

	FieldOrder getAnnotation(Field f) {
		Annotation[] annotations = f.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType() == FieldOrder.class)
				return (FieldOrder) a;
		}

		return null;
	}

	Annotation getAnnotation(Field f, Class<?> cls) {
		for (Annotation a : f.getAnnotations()) {
			if (a.annotationType() == cls)
				return a;
		}

		return null;
	}

	int getFieldLength(Field f) {
		FieldOrder fo = getAnnotation(f);
		if (fo == null)
			return -1;

		return fo.length();
	}
	
	void setByteToObject(Field f, Object obj, ByteBuffer buffer, int len) throws IllegalAccessException {
		try {
			for (int j = 0; j < len; j++) {
				f.set(obj, buffer.get());
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}		
	}
	
	void setShortToObject(Field f, Object obj, ByteBuffer buffer, int len) throws IllegalAccessException {
		try {
			for (int j = 0; j < len; j++) {
				f.set(obj, buffer.getShort());
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}

	void setIntToObject(Field f, Object obj, ByteBuffer buffer, int len) throws IllegalAccessException {
		try {
			for (int j = 0; j < len; j++) {
				f.set(obj, buffer.getInt());
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}	
	}
	
	void setLongToObject(Field f, Object obj, ByteBuffer buffer, int len) throws IllegalAccessException {
		try {
			for (int j = 0; j < len; j++) {
				f.set(obj, buffer.getLong());
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}
	
	void setFloatToObject(Field f, Object obj, ByteBuffer buffer, int len) throws IllegalAccessException {
		try {
			for (int j = 0; j < len; j++) {
				f.set(obj, buffer.getFloat());
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}
	
	void setDoubleToObject(Field f, Object obj, ByteBuffer buffer, int len) throws IllegalAccessException {
		try {
			for (int j = 0; j < len; j++) {
				f.set(obj, buffer.getDouble());
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}
	
	void setCharToObject(Field f, Object obj, ByteBuffer buffer, int len) throws IllegalAccessException {
		try {
			for (int j = 0; j < len; j++) {
				Array.set(f.get(obj), j, (char) buffer.get());
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}

	void copyByteToBuffer(byte[] arr, ByteBuffer buffer) throws IllegalAccessException {
		try {
			for (byte b : arr) {
				buffer.put(b);
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}

	void copyShortToBuffer(short[] arr, ByteBuffer buffer) throws IllegalAccessException {
		try {
			for (short b : arr) {
			buffer.putShort(b);
		}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
		
	}

	void copyIntToBuffer(int[] arr, ByteBuffer buffer) throws IllegalAccessException {
		try {
			for (int b : arr) {
				buffer.putInt(b);
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}

	void copyLongToBuffer(long[] arr, ByteBuffer buffer) throws IllegalAccessException {
		try {
			for (long b : arr) {
				buffer.putLong(b);
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}

	void copyFloatToBuffer(float[] arr, ByteBuffer buffer) throws IllegalAccessException {
		try {
			for (float b : arr) {
				buffer.putFloat(b);
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}

	void copyDoubleToBuffer(double[] arr, ByteBuffer buffer) throws IllegalAccessException {
		try {
			for (double b : arr) {
				buffer.putDouble(b);
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}

	void copyCharToBuffer(char[] arr, ByteBuffer buffer) throws IllegalAccessException {
		try {
			for (char b : arr) {
				buffer.put((byte) b);
			}
		}
		catch (Exception e) {
			throw new IllegalAccessException(e.getMessage());
		}
	}
}
