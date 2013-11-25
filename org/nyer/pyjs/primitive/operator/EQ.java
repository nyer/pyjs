package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Boolean;

public class EQ extends ValueOp {

	public EQ() {
		super("==", new String[] {"number", "number"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object op1 = eval(env, arguments[0]);
		Object op2 = eval(env, arguments[1]);
		
		return Boolean.valueOf(op1.equals(op2));
	}

}
