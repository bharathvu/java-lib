package com.fo.build;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.fo.exception.ObjectDeSerializeException;
import com.fo.exception.ObjectSerializeException;
import com.fo.util.CommonsUtil;

public class SerializeObj {
	private Object obj;
	private ByteBuffer buffer;
	private NavigableMap<Integer, Field> map = new TreeMap<Integer, Field>();
	private SerializationCommons coms = new SerializationCommons();

	public SerializeObj(Object obj) {
		this.obj = obj;
		this.buffer = ByteBuffer.allocate(128);
	}

	public SerializeObj(Object obj, int size) {
		this.obj = obj;
		this.buffer = ByteBuffer.allocate(size);
	}

	public ByteBuffer serializeObj() throws ObjectSerializeException {
		if (!coms.verifyClass(obj.getClass())) {
			throw new ObjectSerializeException("class level annotation missing ");
		}

		coms.buildFieldsMap(obj.getClass(), map);

		if (!coms.verifyMapOrder(map)) {
			throw new ObjectSerializeException("field order out of sequence");
		}

		serializeFields();

		buffer.flip();
		
		return buffer;
	}

	private void serializeFields() throws ObjectSerializeException {
		try {
			for (Integer i : new TreeSet<Integer>(map.keySet())) {
				setValueObjArray(i);
			}
		}
		catch (Exception e) {
			throw new ObjectSerializeException(e);
		}
	}
	
	private void setValueObjArray(int i) throws ObjectSerializeException {
		if (map.get(i).getType().isArray()) {
			setObjectArrayBuffer(map.get(i));
		}
		else {
			setObjectBuffer(map.get(i));
		}
	}
	
	private void setObjectArrayBuffer(Field f) throws ObjectSerializeException {
		try {
			if (f.getType().getComponentType() == byte.class || f.getType().getComponentType() == Byte.class) {
				coms.copyByteToBuffer((byte[]) f.get(obj), buffer);
			}
			else if (f.getType().getComponentType() == short.class || f.getType().getComponentType() == Short.class) {
				coms.copyShortToBuffer((short[]) f.get(obj), buffer);
			}
			else if (f.getType().getComponentType() == int.class || f.getType().getComponentType() == Integer.class) {
				coms.copyIntToBuffer((int[]) f.get(obj), buffer);
			}
			else if (f.getType().getComponentType() == long.class || f.getType().getComponentType() == Long.class) {
				coms.copyLongToBuffer((long[]) f.get(obj), buffer);
			}
			else if (f.getType().getComponentType() == float.class || f.getType().getComponentType() == Float.class) {
				coms.copyFloatToBuffer((float[]) f.get(obj), buffer);
			}
			else if (f.getType().getComponentType() == double.class || f.getType().getComponentType() == Double.class) {
				coms.copyDoubleToBuffer((double[]) f.get(obj), buffer);
			}
			else if (f.getType().getComponentType() == char.class || f.getType().getComponentType() == Character.class) {
				coms.copyCharToBuffer((char[]) f.get(obj), buffer);
			}
			else {
				throw new ObjectDeSerializeException("object not found " + f.getType());
			}
		}
		catch (Exception e) {
			throw new ObjectSerializeException(e);
		}
	}

	private void setObjectBuffer(Field f) throws ObjectSerializeException {
		try {
			if (f.getType() == byte.class || f.getType() == Byte.class) {
				buffer.put((Byte) f.get(obj));
			}
			else if (f.getType() == short.class || f.getType() == Short.class) {
				buffer.putShort((Short) f.get(obj));
			}
			else if (f.getType() == int.class || f.getType() == Integer.class) {
				buffer.putInt((Integer) f.get(obj));
			}
			else if (f.getType() == long.class || f.getType() == Long.class) {
				buffer.putLong((Long) f.get(obj));
			}
			else if (f.getType() == float.class || f.getType() == Float.class) {
				buffer.putFloat((Float) f.get(obj));
			}
			else if (f.getType() == double.class || f.getType() == Double.class) {
				buffer.putDouble((Double) f.get(obj));
			}
			else if (f.getType() == String.class) {
				byte[] arr = CommonsUtil.stringToCharArray((String) f.get(obj), coms.getFieldLength(f));
				buffer.put(arr);
			}
		}
		catch (Exception e) {
			throw new ObjectSerializeException(e);
		}
	}
}
