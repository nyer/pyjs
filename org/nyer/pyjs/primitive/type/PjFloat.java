package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.Env;

public class PjFloat extends Value {
	public PjFloat(float value) {
		super("float str", value);
	}

	@Override
	public String getTypeStr(Env env) {
		return "float";
	}
	
	@Override
	public Float getValue() {
		return (Float) super.getValue();
	}
	
	public static PjFloat valueOf(final float v) {
		return new PjFloat(v);
	}
}
