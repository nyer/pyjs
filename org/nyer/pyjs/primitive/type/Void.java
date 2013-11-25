package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.Env;

public class Void extends Value {

	public Void() {
		super(null, "");
	}
	
	@Override
	public String getTypeStr(Env env) {
		return "void";
	}
	
	@Override
	public String toString() {
		return "void";
	}
}
