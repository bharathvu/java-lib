package com.fo.test;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.fo.exception.ObjectDeSerializeException;
import com.fo.exception.ObjectSerializeException;
import com.fo.test.pojo.User;
import com.fo.util.DeSerializationCommonsUtil;
import com.fo.util.SerializationCommonsUtil;

public class TestSerialize {

	public static void main(String[] args) throws ObjectSerializeException, ObjectDeSerializeException {
		ByteBuffer buffer = SerializationCommonsUtil.getByteBuffer(getUser());
		System.out.println(buffer);
		System.out.println(Arrays.toString(buffer.array()));
		System.out.println(DeSerializationCommonsUtil.getObject(User.class, buffer));
	}
	
	public static User getUser() {
		User user = new User();
		user.id = 99;
		user.ids=33;
		user.no=22;
		user.name = "Diwakar";
		System.arraycopy("Gopinatha".toCharArray(), 0, user.names, 0, "Gopinatha".length());
		
		return user;
	}

}
