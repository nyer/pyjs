package org.nyer.lang.primitive;

public class Integer {
	private int value;
	public Integer(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Integer: " + value;
	}
}
