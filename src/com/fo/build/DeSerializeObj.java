package com.fo.build;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.fo.exception.ObjectDeSerializeException;
import com.fo.exception.ObjectSerializeException;

public class DeSerializeObj {
	private Object obj;
	private ByteBuffer buffer;
	private Class<?> target;
	private NavigableMap<Integer, Field> map = new TreeMap<Integer, Field>();
	private SerializationCommons coms = new SerializationCommons();

	public DeSerializeObj(Class<?> target, ByteBuffer buffer) {
		this.target = target;
		this.buffer = buffer;
	}

	public Object deSerializeObj() throws ObjectDeSerializeException {
		if (!coms.verifyClass(target)) {
			throw new ObjectDeSerializeException("class level annotation missing ");
		}

		try {
			coms.buildFieldsMap(target, map);
		}
		catch (ObjectSerializeException e) {
			throw new ObjectDeSerializeException(e);
		}

		if (!coms.verifyMapOrder(map)) {
			throw new ObjectDeSerializeException("field order out of sequence");
		}

		return deSerializeFields();
	}

	public Object deSerializeFields() throws ObjectDeSerializeException {
		try {
			buffer.rewind();
			obj = target.newInstance();

			for (Integer i : new TreeSet<Integer>(map.keySet())) {
				setValueObjectArray(i);
			}

			return obj;
		}
		catch (Exception e) {
			throw new ObjectDeSerializeException(e);
		}
	}
	
	private void setValueObjectArray(int i) throws IllegalArgumentException, IllegalAccessException, ObjectDeSerializeException {
		if (map.get(i).getType().isArray()) {
			Object arr = map.get(i).get(obj);
			setObjArray(map.get(i), arr == null ? 1 : Array.getLength(arr));
		}
		else {
			setObj(map.get(i));
		}
	}

	private void setObjArray(Field f, int len) throws ObjectDeSerializeException {
		try {
			if (f.getType() == byte.class) {
				coms.setByteToObject(f, obj, buffer, len);
			}
			else if (f.getType() == short.class) {
				coms.setShortToObject(f, obj, buffer, len);
			}
			else if (f.getType() == int.class) {
				coms.setIntToObject(f, obj, buffer, len);
			}
			else if (f.getType() == long.class) {
				coms.setLongToObject(f, obj, buffer, len);
			}
			else if (f.getType() == float.class) {
				coms.setFloatToObject(f, obj, buffer, len);
			}
			else if (f.getType().getComponentType() == double.class) {
				coms.setDoubleToObject(f, obj, buffer, len);
			}
			else if (f.getType().getComponentType() == char.class) {
				coms.setCharToObject(f, obj, buffer, len);
			}
			else {
				throw new ObjectDeSerializeException("object not found");
			}
		}
		catch (Exception e) {
			throw new ObjectDeSerializeException(e);
		}
	}

	private void setObj(Field f) throws ObjectDeSerializeException {
		try {
			if (f.getType() == byte.class || f.getType() == Byte.class) {
				f.set(obj, buffer.get());
			}
			else if (f.getType() == short.class || f.getType() == Short.class) {
				f.set(obj, buffer.getShort());
			}
			else if (f.getType() == int.class || f.getType() == Integer.class) {
				f.set(obj, buffer.getInt());
			}
			else if (f.getType() == long.class || f.getType() == Long.class) {
				f.set(obj, buffer.getLong());
			}
			else if (f.getType() == float.class || f.getType() == Float.class) {
				f.set(obj, buffer.getFloat());
			}
			else if (f.getType() == double.class || f.getType() == Double.class) {
				f.set(obj, buffer.getDouble());
			}
			else if (f.getType() == String.class) {
				f.set(obj, new String(Arrays.copyOfRange(buffer.array(), buffer.position(),
						buffer.position() + coms.getFieldLength(f))));
			}
		}
		catch (Exception e) {
			throw new ObjectDeSerializeException(e);
		}
	}
}
