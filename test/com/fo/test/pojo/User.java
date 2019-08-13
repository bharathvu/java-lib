package com.fo.test.pojo;

import java.util.Arrays;

import com.fo.annotation.FieldOrder;
import com.fo.annotation.SelObject;

@SelObject
public class User {

	@FieldOrder(2)
	public int id;

	@FieldOrder(1)
	public Integer ids;

	@FieldOrder(3)
	public long no;

	@FieldOrder(4)
	public char[] names = new char[20];

	@FieldOrder(value = 5, length=10)
	public String name;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", ids=" + ids + ", no=" + no + ", names=" + Arrays.toString(names) + ", name=" + name
				+ "]";
	}

}
