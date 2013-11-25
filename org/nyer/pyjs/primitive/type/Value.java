package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.AbstractFun;

public abstract class Value extends AbstractFun {
	private Object value;
	
	public Value(String name, String parameter, Object value) {
		super(name, new String[] {parameter});
		this.value = value;
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		return value;
	}
	
	public Object getValue() {
		return value;
	}
	
	@Override
	public java.lang.String toString() {
		return "type: " + getName() + ", value: " + getValue();
	}
}
