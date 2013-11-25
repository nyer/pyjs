package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.Env;


public class PjInteger extends Value  {

	public PjInteger(int value) {
		super("integer str", value);
	}
	
	@Override
	public String getTypeStr(Env env) {
		return "integer";
	}
	
	@Override
	public Integer getValue() {
		return (Integer) super.getValue();
	}
	
	public static PjInteger valueOf(final int v) {
		return new PjInteger(v);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj instanceof Value == false)
			return false;
		Value objValue = (Value) obj;
		return this.value.equals(objValue.getValue());
	}
}
