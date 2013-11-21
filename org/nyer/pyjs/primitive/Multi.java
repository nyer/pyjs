package org.nyer.pyjs.primitive;

import org.nyer.pyjs.AbstractFun;
import org.nyer.pyjs.Env;

public class Multi extends AbstractFun {
	public Multi() {
		super("*", new String[] {"op1", "op2"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object arg1 = arguments[0];
		Object arg2 = arguments[1];
		
		Integer op1 = (Integer) toValue(env, arg1);
		Integer op2 = (Integer) toValue(env, arg2);
		
		return new Integer(op1.getValue() * op2.getValue());
	}
}