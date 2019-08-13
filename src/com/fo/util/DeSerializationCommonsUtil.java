package com.fo.util;

import java.nio.ByteBuffer;

import com.fo.build.DeSerializeObj;
import com.fo.exception.ObjectDeSerializeException;

public class DeSerializationCommonsUtil {
	private DeSerializationCommonsUtil() {
	}
	
	public static Object getObject(Class<?> cls, ByteBuffer buffer) throws ObjectDeSerializeException {
		DeSerializeObj obj = new DeSerializeObj(cls, buffer);
		return obj.deSerializeObj();
	}
}
