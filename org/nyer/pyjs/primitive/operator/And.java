package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.AbstractFun;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Boolean;

public class And extends AbstractFun {

	public And() {
		super("&&", new String[] {"boolean", "boolean"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object arg1 = toValue(env, arguments[0]);
		
		if (arg1 instanceof Boolean == false)
			throw new Exception("boolean expression expected, but found: " + arg1);
		
		if (((Boolean) arg1).getValue() == true) {
			Object arg2 = toValue(env, arguments[1]);
			if (arg2 instanceof Boolean == false) 
				throw new Exception("boolean expression expected , but found: " + arg2);
			if (((Boolean) arg2).getValue()) {
				return Boolean.True;
			}
		}
		
		
		return Boolean.False;
	}

}
