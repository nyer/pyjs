package org.nyer.pyjs.primitive.type;



public class Float extends Value {

	public Float(float value) {
		super("float", "float str", value);
	}

	public static Float valueOf(final float v) {
		return new Float(v);
	}
}
