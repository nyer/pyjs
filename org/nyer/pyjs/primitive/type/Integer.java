package org.nyer.pyjs.primitive.type;


public class Integer extends Value  {

	public Integer(int value) {
		super("integer", "integer str", value);
	}

	public static Integer valueOf(final int v) {
		return new Integer(v);
	}
}
