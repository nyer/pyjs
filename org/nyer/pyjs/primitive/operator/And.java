package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Boolean;

public class And extends ValueOp {

	public And() {
		super("&&", new String[] {"boolean", "boolean"});
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		boolean v = checkBoolOperand(env, arguments[0]);
		if (v) {
			v = checkBoolOperand(env, arguments[1]);
			if (v) {
				return Boolean.True;
			}
		}
		
		return Boolean.False;
	}

}
