package org.nyer.pyjs.primitive.type;

public class Float {
	private float value;
	
	public Float(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
