package org.nyer.lang.primitive;

import org.nyer.lang.AbstractFun;
import org.nyer.lang.Env;

public class Value extends AbstractFun {

	public Value() {
		super("value", null);
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object arg1 = arguments[0];
		
		return toValue(env, arg1);
	}
}
