package org.nyer.lang.primitive;

import org.nyer.lang.AbstractFun;
import org.nyer.lang.Env;

public class Add extends AbstractFun {
	public Add() {
		super("+", new String[] {"op1", "op2"});
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object arg1 = arguments[0];
		Object arg2 = arguments[1];
		
		Integer op1 = (Integer) toValue(env, arg1);
		Integer op2 = (Integer) toValue(env, arg2);
		

		return new Integer(op1.getValue() + op2.getValue());
	}
}
