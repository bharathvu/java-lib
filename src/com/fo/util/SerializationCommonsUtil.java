package com.fo.util;

import java.nio.ByteBuffer;

import com.fo.build.SerializeObj;
import com.fo.exception.ObjectSerializeException;

public class SerializationCommonsUtil {
	private SerializationCommonsUtil() {
	}
	
	public static ByteBuffer getByteBuffer(Object obj) throws ObjectSerializeException {
		SerializeObj seObj = new SerializeObj(obj);
		return seObj.serializeObj();
	}
	
	public static ByteBuffer getByteBuffer(Object obj, int size) throws ObjectSerializeException {
		SerializeObj seObj = new SerializeObj(obj, size);
		return seObj.serializeObj();
	}
}
