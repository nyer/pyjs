package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.Env;

public class PjString extends Value {

	public PjString(String value) {
		super("string", value);
	}

	@Override
	public String getTypeStr(Env env) {
		return "String";
	}
	
	@Override
	public String getValue() {
		return (String) super.getValue();
	}
}
