# java-lib
Field Ordering during Reflection API using Annotation

A Java program to convert POJOs (Plain Old Java Objects) to bytes using Reflection with certain ordering.

Usage:

Use @SelObject annotation on Class level

Use @FieldOrder annotation on Field level. This annotation represents the ordering level while using Reflection API. The field which does not contain the annotation will be ignored.

e.g.:

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

	@FieldOrder(value = 5, length=10) //for strings, length is mandatory
	public String name;
  }
