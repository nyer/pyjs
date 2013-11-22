package org.nyer.pyjs.primitive.type;

public class Boolean {
	private boolean value;
	public Boolean(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public static Boolean valueOf(String str) {
		if (str.equals("true"))
			return True;
		else
			return False;
	}
	
	public static Boolean valueOf(boolean v) {
		if (v)
			return True;
		else
			return False;
	}
	
	public static Boolean True = new Boolean(true);
	public static Boolean False = new Boolean(false);
	@Override
	public String toString() {
		return value + "";
	}
}
